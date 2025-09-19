# Database Connection

## Test Environment

In the `src/test/resources` directory, edit the following files to configure the database connection:

- `TestsBanqueManager-context.xml`
- `TestsDaoHibernate-context.xml`

Update the database connection properties as shown below:

```xml
<property name="url"
          value="jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/brochot6u_QualDevTest" />
<property name="username" value="brochot6u_appli" />
<property name="password" value="32304747" />
