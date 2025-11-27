# 📘 Database Connection — Documentation

## Test Environment Configuration

To configure the database connection used during tests, edit the following files located in:

```
src/test/resources/
```

Files to update:

- `TestsBanqueManager-context.xml`
- `TestsDaoHibernate-context.xml`

Use the following configuration:

```xml
<bean id="dataSource" scope="singleton"
      class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">

    <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />

    <property name="url"
              value="jdbc:mysql://51.91.10.125:3306/qualdev_test?serverTimezone=UTC&amp;useSSL=false" />

    <property name="username" value="QualDev" />

    <property name="password" value="bkJb3JQ479r5Gt" />

    <!-- Required to ensure that tests do not auto-commit changes -->
    <property name="defaultAutoCommit" value="false" />
</bean>
```

---

## 📡 Deploying the Test Database on a VPS

The test database is deployed using the **Dockerfile located in the `/db` directory** of the project and is exposed at:

```
51.91.10.125:3306
```

### 🔧 How Deployment Works

The Docker Compose setup launches three services:

1. **app**
    - Container: `asbank-app`
    - Exposes port `8080`
    - Connects to the database using environment variables `DB_HOST`, `DB_USER`, `DB_PASSWORD`.

2. **db**
    - Container: `mysql-db`
    - Exposes port `3306` for MySQL
    - Persists data using Docker volume `db_data`.

3. **phpMyAdmin**
    - Container: `phpmyadmin`
    - Exposes port `9090` on the VPS
    - Connects to the MySQL service (`db`) using credentials `QualDev` / `bkJb3JQ479r5Gt`
    - Provides a web interface to browse tables, execute queries, and administer the database.

**Access phpMyAdmin** via:

```
http://51.91.10.125:9090
```

---

### 🌍 Benefits of This Setup

- **Reproducible environment** — same database structure everywhere (local, VPS, CI).
- **Portable** — deploying to another server only requires running Docker Compose.
- **Isolation** — database runs in a container, no interference with host system.
- **Web administration** — easy access to phpMyAdmin at port 9090.
- **Persistent storage** — data survives container restarts through Docker volumes.

---

## 📁 Directory Structure

```
project/
├── docker-compose.yml   ← Launches app, MySQL, and phpMyAdmin
├── Dockerfile           ← App Dockerfile
└── db/
    ├── Dockerfile        ← Builds MySQL container
    ├── init.sql          ← Initialization script
    └── scripts/          ← SQL scripts (manual use only)
```
