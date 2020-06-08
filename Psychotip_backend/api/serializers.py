from rest_framework import serializers
from rest_framework.validators import UniqueValidator

from api.models import User, Klien, Admin, Spesialisasi, Psikolog, \
    JadwalPsikolog, Notifikasi, Konsultasi, ChatContent, Artikel, RumahSakit, Quotes


class UserSerializer(serializers.ModelSerializer):
    username = serializers.CharField(
        max_length=15, required=True, validators=[UniqueValidator(queryset=User.objects.all())]
    )
    email = serializers.EmailField(
        max_length=254,
        required=True,
        validators=[UniqueValidator(queryset=User.objects.all())]
    )
    password = serializers.CharField(
        min_length=8,
        max_length=500,
        required=True,
        write_only=True)
    name = serializers.CharField(
        max_length=25, required=True
    )
    birthday = serializers.CharField(
        max_length=10, required=True
    )
    gender = serializers.CharField(
        max_length=9, required=True
    )
    address = serializers.CharField(
        max_length=200, required=True
    )
    profile_picture = serializers.ImageField(
        default='api/static/profile_pic/default-user.png'
    )
    role = serializers.CharField(max_length=10, required=True)

    def create(self, validated_data: User):
        user = User.objects.create(username=validated_data['username'],
                                   email=validated_data['email'],
                                   password=validated_data['password'],
                                   name=validated_data['name'],
                                   birthday=validated_data['birthday'],
                                   gender=validated_data['gender'],
                                   address=validated_data['address'],
                                   profile_picture=validated_data['profile_picture'],
                                   role=validated_data['role'])
        return user

    def update(self, instance, validated_data):
        instance.email = validated_data['email']
        instance.name = validated_data['name']
        instance.birthday = validated_data['birthday']
        instance.gender = validated_data['gender']
        instance.address = validated_data['address']

        instance.save()
        return instance

    class Meta:
        model = User
        fields = ('username', 'email', 'password', 'name', 'birthday',
                  'gender', 'address', 'profile_picture', 'role')
        write_only_fields = 'password'


class KlienSerializer(serializers.ModelSerializer):
    class Meta:
        model = Klien
        fields = ('username', 'email', 'password', 'name', 'birthday',
                  'gender', 'address', 'profile_picture', 'token')


class AdminSerializer(serializers.ModelSerializer):
    class Meta:
        model = Admin
        fields = ('username', 'email', 'name')


class SpesialisasiSerializer(serializers.ModelSerializer):
    class Meta:
        model = Spesialisasi
        fields = ('name', 'nama_spesialisasi')


class PsikologSerializer(serializers.ModelSerializer):
    spesialisasi = SpesialisasiSerializer(many=True)

    class Meta:
        model = Psikolog
        fields = (
            'username', 'email', 'password', 'name', 'birthday', 'gender',
            'address', 'profile_picture', 'spesialisasi')


class JadwalPsikologSerializer(serializers.ModelSerializer):
    psikolog = serializers.CharField(max_length=15, required=True)
    hari = serializers.CharField(max_length=7, required=True)
    jam_kosong_start = serializers.TimeField(required=True)
    jam_kosong_end = serializers.TimeField(required=True)

    def create(self, validated_data: JadwalPsikolog):
        jadwal = JadwalPsikolog.objects.create(psikolog=validated_data["psikolog"],
                                               hari=validated_data["hari"],
                                               jam_kosong_start=validated_data["jam_kosong_start"],
                                               jam_kosong_end=validated_data["jam_kosong_end"])
        return jadwal

    def update(self, instance, validated_data):
        instance.hari = validated_data['hari']
        instance.jam_kosong_start = validated_data['jam_kosong_start']
        instance.jam_kosong_end = validated_data['jam_kosong_end']

        instance.save()
        return instance

    class Meta:
        model = JadwalPsikolog
        fields = ('psikolog', 'hari', 'jam_kosong_start', 'jam_kosong_end')


class NotifikasiSerializer(serializers.ModelSerializer):
    class Meta:
        model = Notifikasi


class KonsultasiSerializer(serializers.ModelSerializer):
    id_konsul = serializers.CharField(max_length=10, required=True)
    klien = serializers.CharField(max_length=15, required=True)
    spesialisasi = serializers.CharField(max_length=1, required=True)
    tanggal = serializers.CharField(max_length=10, required=True)
    waktu = serializers.TimeField(required=True)
    durasi = serializers.CharField(max_length=1, required=True)

    def create(self, validated_data: Konsultasi):
        consultation_schedule = Konsultasi.objects.create(id_konsul=validated_data['id_konsul'],
                                                          klien=validated_data['klien'],
                                                          spesialisasi=validated_data[
                                                              'spesialisasi'],
                                                          tanggal=validated_data['tanggal'],
                                                          waktu=validated_data['waktu'],
                                                          durasi=validated_data['durasi'])
        return consultation_schedule

    class Meta:
        model = Konsultasi
        fields = ('id_konsul', 'klien', 'tanggal', 'waktu', 'spesialisasi', 'durasi')


class ChatContentSerializer(serializers.ModelSerializer):
    class Meta:
        model = ChatContent
        fields = ('id_konsul', 'konten')


class ArtikelSerializer(serializers.ModelSerializer):
    class Meta:
        model = Artikel
        fields = ('id', 'judul', 'konten')


class RumahSakitSerializer(serializers.ModelSerializer):
    class Meta:
        model = RumahSakit
        fields = ('nama', 'lokasi', 'no_telepon')


class QuoteSerializer(serializers.ModelSerializer):
    class Meta:
        model = Quotes
        fields = ('konten', 'author')
