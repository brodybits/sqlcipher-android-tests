package net.zetetic.tests;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.zetetic.ZeteticApplication;

import java.io.File;
import java.io.IOException;

public class SimpleCorruptDatabaseTest extends SQLCipherTest {

    @Override
    protected void internalSetUp() {
        /* Not needed for this test:
         * setUp();
	 */
    }

    @Override
    public boolean execute(SQLiteDatabase null_database_ignored) {

        File unencryptedDatabase = ZeteticApplication.getInstance().getDatabasePath("unencrypted-corrupt.db");

        try {
            ZeteticApplication.getInstance().extractAssetToDatabaseDirectory("unencrypted-corrupt.db");

            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(unencryptedDatabase, "", null);

            // NOTE: database not expected to be null, but check:
            if (database != null)
                database.close();

            return (database != null);

        } catch (Exception e) {
            // Uncaught exception (not expected):
            return false;
        }
        finally {
            unencryptedDatabase.delete();
        }
    }

    @Override
    public String getName() {
        return "Simple Corrupt Database Test";
    }
}
