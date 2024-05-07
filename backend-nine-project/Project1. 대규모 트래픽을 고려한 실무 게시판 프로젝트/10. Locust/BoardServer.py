from locust import HttpUser, task, between
import random


class BoardServer(HttpUser):
    wait_item = between(1, 2)

    def on_start(self):
        self.client.post("/users/signin", json={"userId": "hamgamsa@gmail.com", "password": "12345"})

    @task
    def view_search(self):
        sortStatus = random.choice(["CATEGORIES", "NEWEST", "OLDEST"])
        categoryId = random.randint(1, 10)
        name = "테스트 게시글" + str(random.randint(1, 100000))
        headers = {"Content-Type": "application/json"}

        data = {
            "sortStatus": sortStatus
            , "categoryId": categoryId
            , "name": name
        }

        self.client.post("/search", json=data, headers=headers)

# 실행: locust -f BoardServer.py
# 스트레스 테스트: 유저 500명, 50씩 증가
# 스파이크 테스트: 유저 1000명, 50씩 증가