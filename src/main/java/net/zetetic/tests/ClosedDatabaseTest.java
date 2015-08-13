package net.zetetic.tests;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import java.util.Locale;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteException;
import net.zetetic.ZeteticApplication;

public class ClosedDatabaseTest extends SQLCipherTest {

    @Override
    public TestResult run() {

        TestResult result = new TestResult(getName(), false);
        try {
            result.setResult(execute(null));
            SQLiteDatabase.releaseMemory();
        } catch (Exception e) {
            Log.v(ZeteticApplication.TAG, e.toString());
        }
        return result;
    }

    @Override
    public boolean execute(SQLiteDatabase null_database_ignored) {

        File testDatabasePath = ZeteticApplication.getInstance().getDatabasePath("closed-db-test.db");

        boolean status = false;

        try {
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(testDatabasePath, "", null);

            database.close();

            status = execute_closed_database_tests(database);
        } catch (Exception e) {
            // Uncaught [unexpected] exception:
            Log.e(ZeteticApplication.TAG, "Unexpected exception", e);
            return false;
        }
        finally {
            testDatabasePath.delete();
        }

        return status;
    }

    boolean execute_closed_database_tests(SQLiteDatabase database) {
        try {
            /* operations that check if db is closed
             * (and [should] throw IllegalStateException): */
            try {
                // should throw IllegalStateException:
                database.beginTransaction();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.beginTransaction() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.beginTransaction() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.endTransaction();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.endTransaction() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.endTransaction() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.setTransactionSuccessful();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setTransactionSuccessful() did NOT throw throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setTransactionSuccessful() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.getVersion();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.getVersion() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.getVersion() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.setVersion(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setVersion() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setVersion() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.getMaximumSize();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.getMaximumSize() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.getMaximumSize() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.setMaximumSize(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setMaximumSize() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setMaximumSize() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.getPageSize();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.getPageSize() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.getPageSize() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.setPageSize(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setPageSize() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setPageSize() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.compileStatement("SELECT 1;");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.compileStatement() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.compileStatement() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.query("t1", new String[]{"a", "b"}, null, null, null, null, null);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.query() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.query() did throw exception on closed database OK", e);
            }

            // TODO: cover more query functions

            try {
                // should throw IllegalStateException:
                database.execSQL("SELECT 1;");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.execSQL(String) did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.execSQL(String) did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.execSQL("SELECT 1;", new Object[1]);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.execSQL(String, Object[]) did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.execSQL(String, Object[]) did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.rawExecSQL("SELECT 1;");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.rawExecSQL() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.rawExecSQL() did throw exception on closed database OK", e);
            }

            try {
                // [should] throw an exception on a closed database:
                database.changePassword("new-password");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.changePassword(String) did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.changePassword(String) did throw exception on closed database OK", e);
            }

            try {
                // [should] throw an exception on a closed database:
                database.changePassword("new-password".toCharArray());

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.changePassword(char []) did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.changePassword(char []) did throw exception on closed database OK", e);
            }

            /* operations that do not explicitly check if db is closed
             * ([should] throw SQLiteException on a closed database): */

            try {
                // [should] throw an exception on a closed database:
                database.setLocale(Locale.getDefault());

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setLocale() did NOT throw exception on closed database");
                return false;
            } catch (SQLiteException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setLocale() did throw exception on closed database OK", e);
            }

            try {
                // [should] throw an exception on a closed database:
                database.markTableSyncable("aa", "bb");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.markTableSyncable(String, String) did NOT throw exception on closed database");
                return false;
            } catch (SQLiteException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.markTableSyncable(String, String) did throw exception on closed database OK", e);
            }

            try {
                // [should] throw an exception on a closed database:
                database.markTableSyncable("aa", "bb", "cc");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.markTableSyncable(String, String, String) did NOT throw exception on closed database");
                return false;
            } catch (SQLiteException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.markTableSyncable(String, String, String) did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException [since it calls getVersion()]:
                database.needUpgrade(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.needUpgrade() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.needUpgrade() did throw exception on closed database OK", e);
            }

            /* operations that are NOT expected to throw an exception if the database is closed ([should] not crash) */


            /* XXX TODO: these functions should check the db state,
             * TBD either throw or simply return false if the db is closed */
            database.yieldIfContended();
            database.yieldIfContendedSafely();
            database.yieldIfContendedSafely(100);

            database.setLockingEnabled(false);
            database.setLockingEnabled(true);

            database.close();

            database.isReadOnly();
            database.isOpen();

            database.isInCompiledSqlCache("SELECT 1;");
            database.purgeFromCompiledSqlCache("SELECT 1;");
            database.resetCompiledSqlCache();

            database.getMaxSqlCacheSize();

            try {
                // should throw IllegalStateException:
                database.setMaxSqlCacheSize(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setMaxSqlCacheSize() did NOT throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setMaxSqlCacheSize() did throw exception on closed database OK", e);
            }

        } catch (Exception e) {
            // Uncaught [unexpected] exception:
            Log.e(ZeteticApplication.TAG, "Unexpected exception", e);
            return false;
        }

        return true;
    }

    @Override
    public String getName() {
        return "Closed Database Test";
    }
}
