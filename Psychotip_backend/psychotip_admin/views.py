from django.contrib import messages
from django.http import HttpResponseRedirect
from django.shortcuts import render
from django.urls import reverse
from django.views.decorators.csrf import csrf_exempt

from api.models import Admin, User, Quotes

# Create your views here.
RESPONSE = {}

def index(request):
    if 'user_login' in request.session:
        html = 'dashboard_page.html'
    else:
        html = 'login_page.html'
    return render(request, html)

def authenticate_login(request):
    username = request.POST['username']
    password = request.POST['password']

    admin = Admin.objects.filter(username=username)
    if admin.count() != 0:
        user = User.objects.get(username=username)

        if password == user.password:
            request.session['user_login'] = username
            request.session['name'] = user.name
            messages.success(request, 'Login berhasil.')
        else:
            messages.error(request, 'Username atau password salah.')
    else:
        messages.error(request, 'User tidak ditemukan.')
    return HttpResponseRedirect(reverse('psychotip_admin:index'))


def authenticate_logout(request):
    request.session.flush()
    messages.info(request, 'Logout berhasil.')
    return HttpResponseRedirect(reverse('psychotip_admin:index'))

def quote_of_the_day(request):
    html = 'quote_of_the_day.html'
    return render(request, html)

@csrf_exempt
def create_qotd(request):
    konten = request.POST.get('quote', '')
    author = request.POST.get('author', '')

    if konten != '' and author != '':

        quote = Quotes(konten=konten, author=author)
        quote.save()

        messages.success(request, 'Quote berhasil ditambahkan.')

    elif konten == '' and author != '':
        messages.error(request, 'Konten quote tidak boleh kosong')

    elif konten != '' and author == '':
        messages.error(request, 'Author quote tidak boleh kosong')

    else:
        messages.error(request, 'Quote gagal ditambahkan. '
                                'Silahkan isi konten dan author untuk membuat quote.')

    return HttpResponseRedirect('qotd')

def qotd_list(request):
    quotes = Quotes.objects.all()
    RESPONSE['quotes'] = quotes
    html = 'quote_of_the_day_list.html'
    return render(request, html, RESPONSE)
