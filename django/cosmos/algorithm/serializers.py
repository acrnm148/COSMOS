from rest_framework import serializers
from .models import User, Review, Reviewplace, Place

class ReviewPlaceSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Reviewplace
        fields = ['place']

class ReviewSerializer(serializers.ModelSerializer):
    review_place = ReviewPlaceSerializer(read_only=True, many=True)
    
    class Meta:
        model = Review
        fields = ('review_id', 'contents', 'score', 'review_place')

class UserSerializer(serializers.ModelSerializer):
    reviews = ReviewSerializer(read_only=True, many=True)
    
    class Meta:
        model = User
        fields = ('user_seq', 'type1', 'type2', 'user_name', 'reviews')


class PlaceSerializer(serializers.ModelSerializer):
    
    class Meta:
        model = Place
        fields = '__all__'
        
