from django.db import models

class Adjective(models.Model):
    adjective_id = models.BigAutoField(primary_key=True)
    contents = models.CharField(max_length=255, blank=True, null=True)   

    def __str__(self):
        return f'{self.adjective_id} : {self.contents}'

    class Meta:
        managed = False
        db_table = 'adjective'


class Course(models.Model):
    course_id = models.BigAutoField(primary_key=True)
    end_date = models.CharField(max_length=255, blank=True, null=True)   
    name = models.CharField(max_length=255, blank=True, null=True)       
    start_date = models.CharField(max_length=255, blank=True, null=True) 
    sub_category = models.CharField(max_length=255, blank=True, null=True)
    plan = models.ForeignKey('Plan', models.DO_NOTHING, blank=True, null=True)
    date = models.CharField(max_length=255, blank=True, null=True)       
    orders = models.CharField(max_length=255, blank=True, null=True)     
    user_seq = models.ForeignKey('User', models.DO_NOTHING, db_column='user_seq', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'course'


class Courseplace(models.Model):
    course_place_id = models.BigAutoField(primary_key=True)
    orders = models.IntegerField(blank=True, null=True)
    course = models.ForeignKey(Course, models.DO_NOTHING, blank=True, null=True)
    place = models.ForeignKey('Place', models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'courseplace'


class GugunCode(models.Model):
    code = models.CharField(primary_key=True, max_length=255)
    name = models.CharField(max_length=255, blank=True, null=True)       
    sido_code = models.ForeignKey('SidoCode', models.DO_NOTHING, db_column='sido_code', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'gugun_code'


class Image(models.Model):
    image_id = models.BigAutoField(primary_key=True)
    couple_id = models.BigIntegerField(blank=True, null=True)
    image_url = models.CharField(max_length=255, blank=True, null=True)  
    created_time = models.CharField(max_length=255, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'image'


class Noun(models.Model):
    noun_id = models.BigAutoField(primary_key=True)
    contents = models.CharField(max_length=255, blank=True, null=True)   

    class Meta:
        managed = False
        db_table = 'noun'


class Place(models.Model):
    dtype = models.CharField(max_length=31)
    place_id = models.BigAutoField(primary_key=True)
    address = models.CharField(max_length=255, blank=True, null=True)    
    detail = models.CharField(max_length=255, blank=True, null=True)     
    img1 = models.CharField(max_length=255, blank=True, null=True)       
    img2 = models.CharField(max_length=255, blank=True, null=True)       
    img3 = models.CharField(max_length=255, blank=True, null=True)       
    img4 = models.CharField(max_length=255, blank=True, null=True)       
    img5 = models.CharField(max_length=255, blank=True, null=True)       
    latitude = models.CharField(max_length=255, blank=True, null=True)   
    longitude = models.CharField(max_length=255, blank=True, null=True)  
    name = models.CharField(max_length=255, blank=True, null=True)       
    parking_yn = models.CharField(max_length=255, blank=True, null=True) 
    phone_number = models.CharField(max_length=255, blank=True, null=True)
    thumb_nail_url = models.CharField(max_length=255, blank=True, null=True)
    type = models.CharField(max_length=255, blank=True, null=True)       
    acceptable_people = models.CharField(max_length=255, blank=True, null=True)
    bbq_yn = models.CharField(max_length=255, blank=True, null=True)     
    check_in = models.CharField(max_length=255, blank=True, null=True)   
    check_out = models.CharField(max_length=255, blank=True, null=True)  
    cook_yn = models.CharField(max_length=255, blank=True, null=True)    
    gym_yn = models.CharField(max_length=255, blank=True, null=True)     
    karaoke_yn = models.CharField(max_length=255, blank=True, null=True) 
    pickup_yn = models.CharField(max_length=255, blank=True, null=True)  
    refund = models.CharField(max_length=255, blank=True, null=True)     
    reservation_page = models.CharField(max_length=255, blank=True, null=True)
    room_num = models.CharField(max_length=255, blank=True, null=True)   
    room_type = models.CharField(max_length=255, blank=True, null=True)  
    card_yn = models.CharField(max_length=255, blank=True, null=True)    
    day_off = models.CharField(max_length=255, blank=True, null=True)    
    open_time = models.CharField(max_length=255, blank=True, null=True)  
    playground = models.CharField(max_length=255, blank=True, null=True) 
    representative_menu = models.CharField(max_length=255, blank=True, null=True)
    reserve_info = models.CharField(max_length=255, blank=True, null=True)
    smoking_yn = models.CharField(max_length=255, blank=True, null=True) 
    takeout_yn = models.CharField(max_length=255, blank=True, null=True) 
    total_menu = models.CharField(max_length=255, blank=True, null=True) 
    pet_yn = models.CharField(max_length=255, blank=True, null=True)     
    end_date = models.CharField(max_length=255, blank=True, null=True)   
    introduce = models.CharField(max_length=255, blank=True, null=True)  
    price = models.CharField(max_length=255, blank=True, null=True)      
    start_date = models.CharField(max_length=255, blank=True, null=True) 
    taken_time = models.CharField(max_length=255, blank=True, null=True) 
    age_range = models.CharField(max_length=255, blank=True, null=True)  
    open_period = models.CharField(max_length=255, blank=True, null=True)    
    parking_cost = models.CharField(max_length=255, blank=True, null=True)
    open_day = models.CharField(max_length=255, blank=True, null=True)   
    shopping_list = models.CharField(max_length=255, blank=True, null=True)
    stroller_yn = models.CharField(max_length=255, blank=True, null=True)    
    inside_yn = models.CharField(max_length=255, blank=True, null=True)  
    program = models.CharField(max_length=255, blank=True, null=True)    

    class Meta:
        managed = False
        db_table = 'place'


class Plan(models.Model):
    plan_id = models.BigAutoField(primary_key=True)
    end_date = models.CharField(max_length=255, blank=True, null=True)   
    main_category = models.CharField(max_length=255, blank=True, null=True)
    start_date = models.CharField(max_length=255, blank=True, null=True) 
    couple_id = models.BigIntegerField(blank=True, null=True)
    create_time = models.DateTimeField(blank=True, null=True)
    plan_name = models.CharField(max_length=255, blank=True, null=True)  
    update_time = models.DateTimeField(blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'plan'


class RefreshToken(models.Model):
    refresh_token_id = models.BigAutoField(primary_key=True)
    refresh_token = models.CharField(max_length=500, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'refresh_token'


class Review(models.Model):
    review_id = models.BigAutoField(primary_key=True)
    contents = models.CharField(max_length=255, blank=True, null=True)   
    score = models.IntegerField(blank=True, null=True)
    user_seq = models.ForeignKey('User', related_name="reviews", db_column='user_seq', on_delete=models.CASCADE, blank=True, null=True)

    def __str__(self):
        return f'{self.review_id} : {self.contents} : {self.score} : {self.user_seq}'
    
    class Meta:
        managed = False
        db_table = 'review'


class Reviewplace(models.Model):
    review_place_id = models.BigAutoField(primary_key=True)
    place = models.ForeignKey(Place, models.DO_NOTHING, blank=True, null=True)
    review = models.ForeignKey(Review, related_name="review_place", on_delete=models.CASCADE, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'reviewplace'


class Reviewcategory(models.Model):
    review_category_id = models.BigAutoField(primary_key=True)
    review_category_code = models.CharField(max_length=255, blank=True, null=True)
    review = models.ForeignKey(Review, models.DO_NOTHING, blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'reviewcategory'


class SidoCode(models.Model):
    code = models.CharField(primary_key=True, max_length=255)
    name = models.CharField(max_length=255, blank=True, null=True)       

    class Meta:
        managed = False
        db_table = 'sido_code'


class User(models.Model):
    user_seq = models.BigAutoField(primary_key=True)
    age_range = models.CharField(max_length=255, blank=True, null=True)  
    birthday = models.CharField(max_length=255, blank=True, null=True)   
    couple_id = models.BigIntegerField(blank=True, null=True)
    couple_yn = models.CharField(max_length=255, blank=True, null=True)  
    create_time = models.DateTimeField(blank=True, null=True)
    email = models.CharField(max_length=255, blank=True, null=True)      
    phone_number = models.CharField(max_length=255, blank=True, null=True)
    profile_img_url = models.CharField(max_length=255, blank=True, null=True)
    role = models.CharField(max_length=255, blank=True, null=True)       
    type1 = models.CharField(max_length=255, blank=True, null=True)      
    type2 = models.CharField(max_length=255, blank=True, null=True)      
    user_id = models.CharField(max_length=255, blank=True, null=True)    
    user_name = models.CharField(max_length=255, blank=True, null=True)  

    def __str__(self):
        return f'{self.user_seq}'
    
    class Meta:
        managed = False
        db_table = 'user'


class Userplace(models.Model):
    user_place_id = models.BigAutoField(primary_key=True)
    place = models.ForeignKey(Place, models.DO_NOTHING, blank=True, null=True)
    user_seq = models.ForeignKey(User, models.DO_NOTHING, db_column='user_seq', blank=True, null=True)

    class Meta:
        managed = False
        db_table = 'userplace'
