# ProgramingGameServer

## 概要
このリポジトリは、競プロのサーバーを簡易的に実装してみるものです。

## 前提
- OpenJDK 21
- Kotlin 2.1.20
- Docker Engine

## 動作イメージ

1. 問題一覧取得
    ```shell
    $ curl localhost:8080/problem
    ```
   ```
    [{"id":1,"title":"Hello World!"},{"id":2,"title":"足し算"}]
   ```
2. 問題詳細取得
   ```shell
    $ curl localhost:8080/problem/1
   ```
    ```
    {
       "id": 1,
       "title": "Hello World!",
       "question": "Hello World! という文字列を出力してください",
       "examples": [
           {
             "input": null,
             "output": "Hello World!"
           }
       ]
    }
    ``` 
   
3. 問題回答

    3.1. 例1
    ```shell
    $ curl -X POST -H "Content-Type: application/json"    -d '{"language": "python", "code": "print(\"Hello World\"}'  localhost:8080/problem/1/try
    ```
    ```
    正解！
    ```
   3.2. 例2
   ```shell
   $ curl -X POST -H "Content-Type: application/json" -d '{"language": "python", "code": "import sys; line = sys.stdin.readline().strip(); numbers = list(map(int, line.split())); print(numbers[0] + numbers[1])"}' localhost:8080/problem/2/try
   ```
   ```
   正解！
   ```
   
## 実行方法
```shell
$ gradlew build
```

```shell
$ gradlew run
```