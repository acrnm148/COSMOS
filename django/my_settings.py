#my_settings.py
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.mysql', #1
        'NAME': 'cosmos', #2
        'USER': 'root', #3                      
        # 'PASSWORD': ,  #4              
        # 'HOST': 'localhost',   #5                
        'PORT': '3306', #6
    }
}
SECRET_KEY ='비밀번호'
