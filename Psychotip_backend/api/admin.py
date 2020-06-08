from django.contrib import admin
from .models import *

# Register your models here.
admin.site.register(User)
admin.site.register(Klien)

admin.site.register(Admin)
admin.site.register(Spesialisasi)
admin.site.register(Psikolog)
admin.site.register(JadwalPsikolog)

admin.site.register(Notifikasi)
admin.site.register(Konsultasi)
admin.site.register(ChatContent)

admin.site.register(Artikel)

admin.site.register(RumahSakit)
admin.site.register(Quotes)
