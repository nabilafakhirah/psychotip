from django.contrib.messages import get_messages
from django.http import HttpRequest

from django.test import TestCase, Client

from django.urls import resolve

from psychotip_admin.views import index, quote_of_the_day, qotd_list
from api.models import User, Admin



URL_LIST = ['/psychotip_admin/qotd/', '/psychotip_admin/qotd/qotdlist']

# Create your tests here.
class AdminUnitTest(TestCase):


    def setUp(self):
        user = User.objects.create(username='admin1234',
                                   email='admin@me.com',
                                   password='admin12345',
                                   name='Jane Doe',
                                   birthday='1/1/1999',
                                   gender='Perempuan',
                                   address='Jakarta', )

        Admin.objects.create(username=user)

    def test_psychotip_using_index(self):
        found = resolve('/psychotip_admin/')
        self.assertEqual(found.func, index)

    def test_login_with_correct_pass(self):
        request = Client().post('/psychotip_admin/login/',
                                {'username': 'admin1234', 'password': 'admin12345'})
        self.assertEqual(request.status_code, 302)

    def test_login_with_incorrect_uname(self):
        request = Client().post('/psychotip_admin/login/',
                                {'username': 'admin12345', 'password': 'admin12345'})
        self.assertEqual(request.status_code, 302)

    def test_login_with_incorrect_pass(self):
        request = Client().post('/psychotip_admin/login/',
                                {'username': 'admin1234', 'password': 'admin1234'})
        self.assertEqual(request.status_code, 302)

    def test_logout(self):
        request = Client().post('/psychotip_admin/login/',
                                {'username': 'admin1234', 'password': 'admin12345'})
        self.assertEqual(request.status_code, 302)

        request = Client().get('/psychotip_admin/logout/')
        self.assertEqual(request.status_code, 302)

    def test_login_page_is_displayed(self):
        request = Client().get('/psychotip_admin/')
        self.assertTemplateUsed(request, 'login_page.html')

        # def test_dashboard_page_is_displayed(self):

        # session = self.client.session
        # session['user_login'] = 'admin1234'
        # session.save()

        # session['name'] = 'Jane Doe'
        # session.save()

        # for key, value in self.client.session.items():
        #    print('{} => {}'.format(key, value))
        #    response = Client().post('/psychotip_admin/login/',
        #    {'username': 'admin1234', 'password': 'admin12345'})

        #    request = Client().get('/psychotip_admin/')
        #    self.assertTemplateUsed(request, 'dashboard_page.html')

        # def test_dashboard_page_is_displayed(self):
        #    request = Client().post('/psychotip_admin/login/',
        #    {'username': 'admin1234', 'password': 'admin12345'})
        #    self.client.session['user_login'] = 'admin1234'
        #    self.client.session['user_login'] = 'Jane Doe'
        #    response = index(request)
        #    self.assertTemplateUsed(response, 'dashboard_page.html')

    def test_qotd_page_is_displayed(self):
        request = HttpRequest()
        response = quote_of_the_day(request)
        html_response = response.content.decode('utf8')

        self.assertIn("Quote of the Day.", html_response)

    # def test_login_page_is_displayed(self):
    #   request = HttpRequest()
    #    response = index(request)
    #    self.assertTemplateUsed(response, 'login_page.html')

    # def test_dashboard_page_is_displayed(self):
    #    request = Client().post('/psychotip_admin/login/',
    #    {'username': 'admin1234', 'password': 'admin12345'})
    #    self.client.session['user_login'] = 'admin1234'
    #    self.client.session['user_login'] = 'Jane Doe'
    #    response = index(request)
    #    self.assertTemplateUsed(response, 'dashboard_page.html')

    def test_create_quote_is_valid(self):
        request = Client().post('/psychotip_admin/qotd/',
                                {'konten': 'Life is beautiful.', 'author': 'PsychoTeam'})
        self.assertEqual(request.status_code, 200)


    def test_both_field_is_empty(self):
        response = Client().post('/psychotip_admin/createqotd/', {'quote': '', 'author': ''})

        messages = [m.message for m in get_messages(response.wsgi_request)]
        self.assertIn('Quote gagal ditambahkan. '
                      'Silahkan isi konten dan author untuk membuat quote.', messages)

        self.assertEqual(response.status_code, 302)

    def test_content_field_is_empty(self):
        response = Client().post('/psychotip_admin/createqotd/',
                                 {'quote': '', 'author': 'PsychoTeam'})

        messages = [m.message for m in get_messages(response.wsgi_request)]
        self.assertIn('Konten quote tidak boleh kosong', messages)

        self.assertEqual(response.status_code, 302)

    def test_author_field_is_empty(self):

        response = Client().post('/psychotip_admin/createqotd/',
                                 {'quote': 'Life is beautiful', 'author': ''})

        messages = [m.message for m in get_messages(response.wsgi_request)]
        self.assertIn('Author quote tidak boleh kosong', messages)

        self.assertEqual(response.status_code, 302)

    def test_quotes_list_url_exist(self):
        response = Client().get('/psychotip_admin/qotd/qotdlist/')
        self.assertEqual(response.status_code, 200)

    def test_quotes_list_using_func(self):
        found = resolve('/psychotip_admin/qotd/qotdlist/')
        self.assertEqual(found.func, qotd_list)

    def test_show_all_quotes(self):
        quote_1 = 'Test Quote'
        author_1 = 'Test Author'

        quote_data = {'quote': quote_1, 'author': author_1}
        post_quote_data = Client().post('/psychotip_admin/createqotd', quote_data)
        self.assertEqual(post_quote_data.status_code, 302)

        response = Client().get('/psychotip_admin/qotd/qotdlist/')
        html_response = response.content.decode('utf8')

        for data in quote_data.items():
            self.assertIn(data, html_response)

    def test_breadcrumb_menu(self):

        for urls in URL_LIST:
            response = Client().get(urls)
            html_response = response.content.decode('utf8')
            self.assertIn('breadcrumb', html_response)
