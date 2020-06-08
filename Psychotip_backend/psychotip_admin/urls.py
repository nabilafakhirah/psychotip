from django.conf.urls import url
from .views import *

app_name = 'psychotip_admin'

urlpatterns = [
    url(r'^login/', authenticate_login, name='authenticate_login'),
    url(r'^logout/', authenticate_logout, name='authenticate_logout'),
    url(r'^createqotd', create_qotd, name='create_qotd'),
    url(r'^qotd/qotdlist/', qotd_list, name='qotd_list'),
    url(r'^qotd/', quote_of_the_day, name='quote_of_the_day'),
    url(r'^$', index, name='index')
]