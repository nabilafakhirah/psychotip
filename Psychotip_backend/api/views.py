import random

from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from passlib.hash import pbkdf2_sha256

from django.db.models import Max

from .models import User, Klien, Spesialisasi, Psikolog, Quotes, JadwalPsikolog
from .serializers import UserSerializer, QuoteSerializer, KonsultasiSerializer, \
    JadwalPsikologSerializer


class UserCreate(APIView):
    @staticmethod
    def add_specialization(user, specialization):
        try:
            psychologist = Psikolog.objects.get(username=user)
        except Psikolog.DoesNotExist:
            return status.HTTP_400_BAD_REQUEST

        if specialization == 'Psikologi Klinis':
            spesialisasi = Spesialisasi.objects.filter(nama_spesialisasi='1').first()

            if not spesialisasi:
                spesialisasi = Spesialisasi.objects.create(nama_spesialisasi='1')

            psychologist.spesial.add(spesialisasi)

        elif specialization == 'Psikologi Perkembangan':
            spesialisasi = Spesialisasi.objects.filter(nama_spesialisasi='2').first()

            if not spesialisasi:
                spesialisasi = Spesialisasi.objects.create(nama_spesialisasi='2')

            psychologist.spesial.add(spesialisasi)

        elif specialization == 'Psikologi Pendidikan':
            spesialisasi = Spesialisasi.objects.filter(nama_spesialisasi='3').first()

            if not spesialisasi:
                spesialisasi = Spesialisasi.objects.create(nama_spesialisasi='3')

            psychologist.spesial.add(spesialisasi)

        elif specialization == 'Psikologi Sosial':
            spesialisasi = Spesialisasi.objects.filter(nama_spesialisasi='4').first()

            if not spesialisasi:
                spesialisasi = Spesialisasi.objects.create(nama_spesialisasi='4')

            psychologist.spesial.add(spesialisasi)

        elif specialization == 'Psikologi Industri dan Organisasi':
            spesialisasi = Spesialisasi.objects.filter(nama_spesialisasi='5').first()

            if not spesialisasi:
                spesialisasi = Spesialisasi.objects.create(nama_spesialisasi='5')

            psychologist.spesial.add(spesialisasi)

        else:
            return status.HTTP_400_BAD_REQUEST

        return status.HTTP_201_CREATED

    @staticmethod
    def post(request):
        data = request.data.copy()
        hashed = pbkdf2_sha256.hash(data['password'])
        data['password'] = hashed
        data['role'] = data['role'].capitalize()
        serializer = UserSerializer(data=data)

        if serializer.is_valid():
            user = serializer.save()
            print(User.objects.get_queryset().values())
            if user:
                if data['role'] == 'Klien':
                    Klien.objects.create(
                        username=user,
                        token=0
                    )
                elif data['role'] == 'Psikolog':
                    Psikolog.objects.create(
                        username=user,
                        no_SIPP=data['sippNumber'],
                        nama_bank=data['bankName'],
                        no_rekening=data['acctNumber'],
                        nama_rekening=data['acctName']
                    )
                    return Response(serializer.data,
                                    status=UserCreate.add_specialization(user,
                                                                         data['specialization']))

                return Response(serializer.data, status=status.HTTP_201_CREATED)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class UserUpdate(APIView):
    @staticmethod
    def put(request):
        saved_user = User.objects.get(pk=request.data['username'])
        data = request.data
        serializer = UserSerializer(instance=saved_user, data=data, partial=True)
        if serializer.is_valid():
            user = serializer.save()
            if user:
                return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class UserView(APIView):
    @staticmethod
    def get(request, user_id):
        active_user = User.objects.get(pk=user_id)
        serializer = UserSerializer(active_user)
        return Response(serializer.data, status=status.HTTP_200_OK)


class RetrieveQuote(APIView):
    @staticmethod
    def get_random_quote():
        max_id = Quotes.objects.all().aggregate(max_id=Max('id'))['max_id']
        while True:
            primary_key = random.randint(1, max_id)
            quote = Quotes.objects.filter(pk=primary_key).first()
            if quote:
                return quote

    @staticmethod
    def get(request):
        quotes = Quotes.objects.all()

        if not quotes:
            return Response(status=status.HTTP_400_BAD_REQUEST)

        quote = RetrieveQuote.get_random_quote()
        serializer = QuoteSerializer(quote)
        return Response(serializer.data, status=status.HTTP_200_OK)


class PsychologistSchedule(APIView):
    @staticmethod
    def post(request):
        data = request.data.copy()
        serializer = JadwalPsikologSerializer(data=data)
        if serializer.is_valid():
            jadwal = serializer.save()
            if jadwal:
                return Response(serializer.data, status=status.HTTP_201_CREATED)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

    @staticmethod
    def put(request):
        data = request.data
        saved_schedule = JadwalPsikolog.objects.get(psikolog=data['psikolog'])
        serializer = JadwalPsikologSerializer(instance=saved_schedule, data=data, partial=True)
        if serializer.is_valid():
            jadwal = serializer.save()
            if jadwal:
                return Response(serializer.data, status=status.HTTP_202_ACCEPTED)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)


class DeleteSchedule(APIView):
    @staticmethod
    def post(request):
        data = request.data
        jadwal = JadwalPsikolog.objects.get(psikolog=data['psikolog'],
                                            hari=data['hari'],
                                            jam_kosong_start=data['jam_kosong_start'],
                                            jam_kosong_end=data['jam_kosong_end'])
        if jadwal:
            jadwal.delete()
            return Response(status=status.HTTP_200_OK)

        return Response(status=status.HTTP_400_BAD_REQUEST)


class RetrieveSchedule(APIView):
    @staticmethod
    def get(request, user_id):
        data = JadwalPsikolog.objects.filter(psikolog=user_id)
        serializer = JadwalPsikologSerializer(data, many=True)
        return Response(serializer.data, status=status.HTTP_200_OK)


class UserValidationLogin(APIView):
    @staticmethod
    def post(request):
        response = {}
        username = request.data['username']
        password = request.data['password']

        user = User.objects.filter(pk=username).first()
        if user is None:
            response['Validation'] = 'False'
        else:
            if pbkdf2_sha256.verify(password, user.password):
                response['Validation'] = 'True'
            else:
                response['Validation'] = 'False'
        return Response(response, status=status.HTTP_202_ACCEPTED)


FIELD_CHOICES = {
    'Psikologi Klinis': '1',
    'Psikologi Perkembangan': '2',
    'Psikologi Pendidikan': '3',
    'Psikologi Sosial': '4',
    'Psikologi Industri & Organisasi': '5'
}

DURATION_CHOICES = {
    '90 menit': '1',
    '180 menit': '2'
}


class CounselingSchedule(APIView):
    @staticmethod
    def post(request):
        data = request.data.copy()
        data['spesialisasi'] = FIELD_CHOICES.get(data['spesialisasi'])
        data['durasi'] = DURATION_CHOICES.get(data['durasi'])

        serializer = KonsultasiSerializer(data=data)
        if serializer.is_valid():
            counseling_sched = serializer.save()
            if counseling_sched:
                return Response(serializer.data, status=status.HTTP_201_CREATED)
        else:
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
