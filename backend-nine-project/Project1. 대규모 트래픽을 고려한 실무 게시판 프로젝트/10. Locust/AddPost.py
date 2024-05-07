from locust import HttpUser, task, between
import random


class AddPosts(HttpUser):
    wait_item = between(1, 2)

    def on_start(self):
        self.client.post("/users/signin", json={"userId": "hamgamsa@gmail.com", "password": "12345"})

    @task
    def add_post(self):
        self.client.post("/posts", json={
            "name": "테스트 게시글" + str(random.randint(1, 100000))
            , "contents": "테스트 컨텐츠" + str(random.randint(1, 100000))
            , "isAdmin": False
            , "views": 0
            , "categoryId": random.randint(1, 10)
        })

# 실행: locust -f AddPost.py
