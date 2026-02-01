# ⚙️ Project Configuration

Step-by-step instructions to set up and run the project:

---

## 1. 📂 Open the project in IntelliJ IDEA

Simply launch IntelliJ and open the project folder.

---

## 2. 🗄️ Configure the database connection

- Follow the documentation in [db_connexion](./database/db_connexion.md).

---

## 3. 🏗️ Compile the project

- Run the **Maven clean** and **compile** lifecycles.
> 💡 Tip: Consider adding a **Run Configuration** in IntelliJ for convenience.

---

## 4. 🌐 Install Apache Tomcat (v9 or above)

- Ensure you have the proper authorizations to run Tomcat on your system.

---

## 5. ▶️ Add a Run Configuration to automatically run your project on Tomcat

1. In the top-right corner of IntelliJ, click the arrow pointing down, then select **Edit Configurations**.
2. Add a new configuration: **Tomcat Server > Local**.
3. Click **Configure** to set your Tomcat home (default Tomcat installation directory).
4. In the **Before launch** area:
   - Remove `Build`.
   - Add `Build artifact` and select `<your-war-name>:war exploded`.
5. In the **Deployment** tab, add an artifact with `<your-war-name>:war`.

> ℹ️ Note: In the Application context field, make sure to enter the name of your project without the .war extension or :war exploded suffix — just the plain project name.

✅ Your project is now ready to run directly from IntelliJ with Tomcat.
