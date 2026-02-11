# ⚙️ Project Configuration Guide

This document explains **step by step** how to configure and run the project in a **development environment** using IntelliJ IDEA and Spring Boot.

---

## 1. 📂 Open the Project in IntelliJ IDEA

1. Launch **IntelliJ IDEA**
2. Click on **File → Open**
3. Select the **root directory** of the project
4. Wait for IntelliJ to finish:

   * Maven dependency resolution
   * Indexing

✅ At this point, the project should compile without errors.

---

## 2. 🌱 Environment Variables Setup

The project uses environment variables to manage sensitive or environment-specific configuration (database credentials, secrets, etc.).

### 2.1 Create the `.env` file

1. At the **root of the project**, locate the file:

   ```
   .env.dist
   ```
2. Create a copy of this file and rename it to:

   ```
   .env
   ```

### 2.2 Configure the values

Open the `.env` file and fill in the required values.

Example:

```env
DB_HOST=51.91.10.125:3306
DB_NAME=qualdev_test
DB_USER=QualDev
DB_PASSWORD=
JWT_SECRET=
JWT_EXPIRATION_AUTH=3600000
JWT_EXPIRATION_RESET=900000
```

⚠️ **Important**

* The `.env` file **must not be committed** to Git
* `.env.dist` serves as a **template and documentation**

---

## 3. 🚀 Configure and Run the Dev Profile

Spring Boot supports **profiles** to adapt configuration depending on the environment (`dev`, `prod`, etc.).

Here, we configure IntelliJ to run the application with the **`dev` profile**.

---

### 3.1 Create a Maven Run Configuration

1. In IntelliJ, go to the **top-right corner**
2. Click on the **dropdown arrow** next to the Run button
3. Select **Edit Configurations**
4. Click on the **`+` icon**
5. Choose **Maven**

---

### 3.2 Configure the Maven Command

Fill in the configuration as follows:

* **Name**:

  ```
  Spring Boot - Dev
  ```

* **Command line**:

  ```
  spring-boot:run
  ```

* **Profiles**:

  ```
  dev
  ```

This ensures Spring Boot will load:

* `application.properties`
* then override with `application-dev.properties`

---

### 3.3 Run the Application

1. Click **Apply**
2. Click **Run**

If everything is correctly configured, you should see:

* Spring Boot starting successfully
* The `dev` profile activated in the logs:

  ```
  The following profiles are active: dev
  ```

---

### 📝 Note (Troubleshooting)

If an error occurs when starting the application, you can try the following actions **from the Maven panel on the right side of IntelliJ**:

1. Run the Maven lifecycle goal:

   ```
   install
   ```

   This will:

   * Rebuild the project
   * Download dependencies
   * Regenerate generated sources if needed

2. If the project uses OpenAPI code generation, run the following Maven plugin goal:

   ```
   openapi-generator:generate
   ```

   This ensures that all OpenAPI-generated classes are correctly created before launching the application.

After running one or both of these commands, try to **restart the application**.