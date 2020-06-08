from django.urls import reverse

from rest_framework.test import APITestCase
from rest_framework import status

from .models import User, Klien, Spesialisasi, Psikolog, JadwalPsikolog, Konsultasi, Quotes


class APITest(APITestCase):
    def setUp(self):
        self.client_data = {
            'username': 'LightFury',
            'email': 'light@fury.com',
            'password': 'lightfury1234',
            'name': 'Light Fury',
            'birthday': '30/11/1998',
            'gender': 'Laki-laki',
            'address': 'Pondok Cina',
            'role': 'klien'
        }

        self.psychologist_data = {
            'username': 'sarahsechan',
            'email': 'sarah@sechan.com',
            'password': 'inipasswordsarah',
            'name': 'Sarah Sechan',
            'birthday': '20/12/1975',
            'gender': 'Perempuan',
            'address': 'Jakarta Selatan',
            'sippNumber': '000123456789',
            'specialization': 'Psikologi Klinis',
            'bankName': 'BCA',
            'acctNumber': '12345678910',
            'acctName': 'Sarah S.',
            'role': 'Psikolog'
        }

        self.test_quote = Quotes.objects.create(
            konten='It does not matter how slow you go so long as you do not stop.',
            author='Confucius'
        )

        self.create_url = reverse('api:account_create')
        self.retrieve_quote = reverse('api:retrieve_quote')
        self.jadwal_psikolog = reverse('api:psychologist_schedule')
        self.login_validate = reverse('api:login_validate')

    def test_create_user(self):
        response = self.client.post(self.create_url, self.client_data)
        self.assertEqual(User.objects.count(), 1)
        self.assertEqual(Klien.objects.count(), 1)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.data['username'], self.client_data['username'])
        self.assertEqual(response.data['email'], self.client_data['email'])
        self.assertFalse('password' in response.data)

    def test_create_psychologist(self):
        response = self.client.post(self.create_url, self.psychologist_data)

        self.assertEqual(User.objects.count(), 1)
        self.assertEqual(Psikolog.objects.count(), 1)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(response.data['username'], self.psychologist_data['username'])

    def test_add_specialization(self):
        response = self.client.post(self.create_url, self.psychologist_data)

        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Spesialisasi.objects.count(), 1)

        spesialisasi = Spesialisasi.objects.first()

        self.assertEqual(spesialisasi.nama_spesialisasi, '1')

    def test_validate_user_login(self):
        data = {
            'username': 'LightFury',
            'password': 'lightfury1234'
        }

        data_wrong_username = {
            'username': 'mimichan',
            'password': 'mimichan1234'
        }

        data_wrong_password = {
            'username': 'LightFury',
            'password': 'lightfury12345'
        }

        self.client.post(self.create_url, self.client_data)
        response_true = self.client.post(self.login_validate, data)
        self.assertEqual(response_true.status_code, status.HTTP_202_ACCEPTED)
        self.assertEqual(response_true.data['Validation'], 'True')

        response_false1 = self.client.post(self.login_validate, data_wrong_username)
        self.assertEqual(response_false1.status_code, status.HTTP_202_ACCEPTED)
        self.assertEqual(response_false1.data['Validation'], 'False')

        response_false2 = self.client.post(self.login_validate, data_wrong_password)
        self.assertEqual(response_false2.status_code, status.HTTP_202_ACCEPTED)
        self.assertEqual(response_false2.data['Validation'], 'False')

    def test_update_user(self):
        updated_data = {
            'username': 'LightFury',
            'email': 'ifury@gmail.com',
            'password': 'furyisafurry',
            'name': 'Hard Fury',
            'birthday': '1/1/2000',
            'gender': 'Perempuan',
            'address': 'Pondok Labu',
            'role': 'klien'
        }

        self.client.post(self.create_url, self.client_data)

        response = self.client.put(reverse('api:update_user'), updated_data)
        self.assertEqual(response.status_code, status.HTTP_202_ACCEPTED)
        self.assertEqual(response.data['email'], updated_data['email'])
        self.assertEqual(response.data['name'], updated_data['name'])
        self.assertEqual(response.data['birthday'], updated_data['birthday'])
        self.assertEqual(response.data['gender'], updated_data['gender'])
        self.assertEqual(response.data['address'], updated_data['address'])

    def test_retrieve_quote_success(self):
        quote = Quotes.objects.first()

        response = self.client.get(self.retrieve_quote)
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(response.data['konten'], quote.konten)
        self.assertEqual(response.data['author'], quote.author)

    def test_retrieve_quote_failed(self):
        Quotes.objects.all().delete()
        response = self.client.get(self.retrieve_quote)

        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)

    def test_create_psikolog_schedule(self):
        data = {
            'username': 'sarahsechan',
            'email': 'sarah@sechan.com',
            'password': 'inipasswordsarah',
            'name': 'Sarah Sechan',
            'birthday': '20/12/1975',
            'gender': 'Perempuan',
            'address': 'Jakarta Selatan',
            'sippNumber': '000123456789',
            'specialization': 'Psikologi Klinis',
            'bankName': 'BCA',
            'acctNumber': '12345678910',
            'acctName': 'Sarah S.',
            'role': 'Psikolog'
        }

        data_jadwal = {
            'psikolog': 'sarahsechan',
            'hari': 'Senin',
            'jam_kosong_start': '10:00',
            'jam_kosong_end': '16:00'
        }

        self.client.post(self.create_url, data, format='json')
        response = self.client.post(self.jadwal_psikolog, data_jadwal, format='json')
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)

    def test_edit_psikolog_schedule(self):
        data_jadwal = {
            'psikolog': 'LightFury',
            'hari': 'Senin',
            'jam_kosong_start': '10:00',
            'jam_kosong_end': '16:00'
        }

        new_data = {
            'psikolog': 'LightFury',
            'hari': 'Senin',
            'jam_kosong_start': '10:00',
            'jam_kosong_end': '17:00'
        }

        self.client.post(self.jadwal_psikolog, data_jadwal, format='json')
        response = self.client.put(self.jadwal_psikolog, new_data)
        self.assertEqual(response.status_code, status.HTTP_202_ACCEPTED)

    def test_delete_psikolog_schedule(self):
        data_jadwal = {
            'psikolog': 'LightFury',
            'hari': 'Senin',
            'jam_kosong_start': '10:00',
            'jam_kosong_end': '16:00'
        }

        self.client.post(self.jadwal_psikolog, data_jadwal, format='json')
        self.assertEqual(JadwalPsikolog.objects.count(), 1)

        response = self.client.post(reverse('api:delete_schedule'), data_jadwal, format='json')
        self.assertEqual(response.status_code, status.HTTP_200_OK)
        self.assertEqual(JadwalPsikolog.objects.count(), 0)

    def test_create_counseling_schedule_success(self):
        counseling_data = {
            'id_konsul': '2504191',
            'klien': 'LightFury',
            'spesialisasi': 'Psikologi Klinis',
            'tanggal': '2019-05-13',
            'waktu': '13:00',
            'durasi': '180 menit'
        }

        response = self.client.post(self.create_url, self.client_data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Klien.objects.count(), 1)

        response = self.client.post(reverse('api:create_counseling'), counseling_data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Konsultasi.objects.count(), 1)

    def test_create_counseling_schedule_failed(self):
        counseling_data = {
            'id_konsul': '',
            'klien': 'LightFury',
            'spesialisasi': '',
            'tanggal': '',
            'waktu': '',
            'durasi': ''
        }

        response = self.client.post(self.create_url, self.client_data)
        self.assertEqual(response.status_code, status.HTTP_201_CREATED)
        self.assertEqual(Klien.objects.count(), 1)

        response = self.client.post(reverse('api:create_counseling'), counseling_data)
        self.assertEqual(response.status_code, status.HTTP_400_BAD_REQUEST)
        self.assertEqual(Konsultasi.objects.count(), 0)
