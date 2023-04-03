from django.urls import path
from . import views

urlpatterns = [
    path('CF/algorithm/data', views.algorithm),
    path('CF/algorithm/userSeq/<int:user_seq>/', views.userAlgorithm),
    path('test', views.test)
]
