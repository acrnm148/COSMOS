#my_settings.py
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql', #1
        'NAME': 'cosmos', #2
        'USER': 'root', #3                      
        'PASSWORD': 'cosmos1234',  #4              
        'HOST': 'j8e104.p.ssafy.io',   #5
        # 'HOST': 'localhost',   #5                
        'PORT': '3306', #6
    }
}
SECRET_KEY ='django-insecure-!wodkkgg9%wo^54_*v-2h^%u7hsho2)sce%&@7ryn9^a*pz+5l'