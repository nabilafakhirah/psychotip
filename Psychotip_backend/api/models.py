from django.db import models


class User(models.Model):
    username = models.CharField(max_length=15, primary_key=True)
    email = models.EmailField(max_length=254, null=False)
    password = models.CharField(max_length=500, null=False)
    name = models.CharField(max_length=25)
    birthday = models.CharField(max_length=10)
    gender = models.CharField(max_length=9)
    address = models.CharField(max_length=200, default='Jakarta')
    profile_picture = models.ImageField('photo', upload_to="api/static/profile_pic",
                                        default='api/static/profile_pic/default-user.png')
    role = models.CharField(max_length=10)

    def __str__(self):
        return self.username


class Klien(models.Model):
    username = models.ForeignKey(User, on_delete=models.CASCADE)
    token = models.IntegerField(default=0)

    def __str__(self):
        return self.username.username + ' (Klien)'


class Admin(models.Model):
    username = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self):
        return self.username.username + ' (Admin)'


class Spesialisasi(models.Model):
    SPESIALISASI = (
        ('1', 'Klinis'),
        ('2', 'Perkembangan'),
        ('3', 'Pendidikan'),
        ('4', 'Sosial'),
        ('5', 'Industri & Organisasi')
    )
    nama_spesialisasi = models.CharField(max_length=1, choices=SPESIALISASI)

    def __str__(self):
        return self.nama_spesialisasi


class Psikolog(models.Model):
    username = models.ForeignKey(User, on_delete=models.CASCADE)
    scan_ktp = models.ImageField('photo', upload_to="api/static/scan_ktp")
    scan_ijazah = models.ImageField('photo', upload_to="api/static/scan_ijazah")
    nama_rekening = models.CharField(max_length=100)
    nama_bank = models.CharField(max_length=50)
    no_rekening = models.CharField(max_length=30)
    no_SIPP = models.CharField(max_length=50, null=False, default=0000000000)
    spesial = models.ManyToManyField(Spesialisasi)

    def __str__(self):
        return self.username.username + ' (Psikolog)'


class JadwalPsikolog(models.Model):
    psikolog = models.CharField(max_length=15, null=False)
    hari = models.CharField(max_length=7, null=False)
    jam_kosong_start = models.TimeField(default='10:00')
    jam_kosong_end = models.TimeField(default='16:00')

    def __str__(self):
        return self.psikolog


class Notifikasi(models.Model):
    username = models.ForeignKey(User, on_delete=models.CASCADE)
    judul_konten = models.CharField(max_length=100)
    konten = models.CharField(max_length=1000)

    def __str__(self):
        return self.username.username + ', ' + self.judul_konten + ', ' + self.konten


class Konsultasi(models.Model):
    DURASI = (
        ('1', '90 menit'),
        ('2', '180 menit')
    )
    id_konsul = models.CharField(max_length=10, primary_key=True)
    klien = models.CharField(max_length=15)
    psikolog = models.CharField(max_length=15, null=True)
    spesialisasi = models.CharField(max_length=1)
    tanggal = models.CharField(max_length=10)
    waktu = models.TimeField()
    durasi = models.CharField(max_length=1, choices=DURASI)

    def __str__(self):
        waktu_str = self.waktu.__str__()
        return self.id_konsul + ', ' +  ', ' + self.spesialisasi \
               + ', ' + self.klien + ', ' + self.tanggal + ', ' + waktu_str + ', ' + self.durasi


class ChatContent(models.Model):
    id_konsul = models.ForeignKey(Konsultasi, on_delete=models.CASCADE)
    konten = models.CharField(max_length=5000)

    def __str__(self):
        return self.id_konsul + ', ' + self.konten


class Artikel(models.Model):
    id_artikel = models.CharField(max_length=7, primary_key=True)
    judul = models.CharField(max_length=200)
    konten = models.CharField(max_length=20000)

    def __str__(self):
        return self.id_artikel + ', ' + self.judul + ', ' + self.konten


class RumahSakit(models.Model):
    nama = models.CharField(max_length=50)
    lokasi = models.CharField(max_length=150)
    no_telepon = models.CharField(max_length=13)

    def __str__(self):
        return self.nama + ', ' + self.lokasi + ', ' + self.no_telepon


class Quotes(models.Model):
    konten = models.CharField(max_length=500)
    author = models.CharField(max_length=50)

    def __str__(self):
        return self.konten + ' -- ' + self.author
