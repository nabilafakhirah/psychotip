from django.conf.urls import url
from . import views
app_name = 'api'

urlpatterns = [
    url(r'^create_user/$', views.UserCreate.as_view(), name='account_create'),
    url(r'^update_user/$', views.UserUpdate.as_view(), name='update_user'),
    url(r'^user_view/(?P<user_id>\w+)', views.UserView.as_view(), name='user_view'),
    url(r'^retrieve_quote/', views.RetrieveQuote.as_view(), name='retrieve_quote'),
    url(r'^psychologist_schedule/', views.PsychologistSchedule.as_view(), name='psychologist_schedule'),
    url(r'^view_schedule/(?P<user_id>\w+)', views.RetrieveSchedule.as_view(), name='view_schedule'),
    url(r'^delete_schedule/', views.DeleteSchedule.as_view(), name='delete_schedule'),
    url(r'^login_validate/$', views.UserValidationLogin.as_view(), name='login_validate'),
    url(r'^create_counseling/', views.CounselingSchedule.as_view(), name='create_counseling')
]
