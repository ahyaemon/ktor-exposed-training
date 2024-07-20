# Ktor-Exposed-Training

請求書作成を題材に、Ktor を使用して API を作成する。 

JWT によるログインと認証も実装する。

## 技術スタック
- 言語
  - Kotlin
- Web フレームワーク
  - Ktor
- ORM
  - Exposed
- DB Migration
  - Flyway
- DB
  - PostgreSQL
- 認証
  - JWT 

## アーキテクチャ

依存関係の方向が

```
domain 層 <- application 層 <- adapter 層
```

となるように実装。

### domain 層

ドメインロジックを実装する。

### application 層

ドメイン層のオブジェクトを利用して、ビジネスロジックを実装する。
今回リポジトリのインターフェースはここに置くようにした。

### adapter 層

DB(exposed) や HTTP(ktor) などとのやりとりを実装する。

## 動作確認

```shell
# 起動
docker compose up -d

# マイグレーション
./gradlew flywayMigrate

# hello world
curl 'http://localhost:18080'

# ユーザー作成
curl -X POST 'http://localhost:18080/users' -H 'Content-Type: application/json' -d '{"company": {"corporateName": "株式会社テスト", "representativeName": "代表太郎", "phoneNumber": "080-0000-1111", "postCode": "000-2345", "address": "東京都"}, "user": {"name": "テストマン", "mailAddress": "test@example.com", "password": "password-string"}}'

# 作成したユーザーでログイン
curl -X POST 'http://localhost:18080/users/login' -H 'Content-Type: application/json' -d '{"mailAddress": "testa@example.com", "password": "password-string"}'

# 請求書作成
# 取引先ID(supplierID)はテストデータの `0000004JFGVCGQFKE4JWFTZ8QK` を使用
curl -X POST 'http://localhost:18080/invoices' -H 'Authorization: Bearer {ログイン時に発行されたトークン}' -H 'Content-Type: application/json' -d '{"supplierId": "0000004JFGVCGQFKE4JWFTZ8QK", "issuedDate": "2024-06-01", "paymentDueDate": "2024-06-30", "paymentAmount": 10000}'

# 請求書一覧取得
curl 'http://localhost:18080/invoices?dateFrom=2024-06-01&dateTo=2024-07-30&limit=2&offset=0'  -H 'Authorization: Bearer {ログイン時に発行されたトークン}' 
```

## 開発

```shell
# DB 起動
docker compose up -d db

# マイグレーション
./gradlew flywayMigrate
```

## 実装した所感等

- value object を作りすぎかもしれない
  - 型安全になるのは良い
    - 引数の順番を間違えたりしなくなる
    - 同じ String でも別の VO にすれば、個々の要件を満たす実装をそれぞれに組み込める
  - 実装が面倒
  - 実行時にインスタンス作成のオーバーヘッドがある
- data class を使うかどうか
  - data class には private constructor が無いからファクトリメソッドを強制できない
  - assertEqual で比較ができるから使いたいけど、テスト用に private constructor を諦めるかどうか
  - DB から取得したオブジェクトをそのままマッピングする時に constructor があると楽
    - DB からの取得したものもファクトリメソッドを通してバリデーションしたいけど、今回は data class でやる
- ID の採番に ULID を採用
  - DB での自動発行をやめることで、アプリがスケールアウトした時に DB に処理が集中することを避ける
  - アプリ内で発行すると、ドメインモデル作成時に ID が null にならないので取り回しが楽
