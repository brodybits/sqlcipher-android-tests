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

        File unencryptedDatabase = ZeteticApplication.getInstance().getDatabasePath("closed-db-test.db");

        boolean status = false;

        try {
            SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(unencryptedDatabase, "", null);

            database.close();

            status = execute_closed_database_tests(database);
        } catch (Exception e) {
            // Uncaught [unexpected] exception:
            Log.e(ZeteticApplication.TAG, "Unexpected exception", e);
            return false;
        }
        finally {
            unencryptedDatabase.delete();
        }

        return status;
    }

    boolean execute_closed_database_tests(SQLiteDatabase database) {
        try {
            /* operations that check if db is closed (and throw IllegalStateException): */
            try {
                // should throw IllegalStateException:
                database.beginTransaction();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.beginTransaction() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.beginTransaction() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.endTransaction();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.endTransaction() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.endTransaction() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.setTransactionSuccessful();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setTransactionSuccessful() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setTransactionSuccessful() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.getVersion();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.getVersion() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.getVersion() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.setVersion(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setVersion() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setVersion() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.getMaximumSize();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.getMaximumSize() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.getMaximumSize() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.setMaximumSize(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setMaximumSize() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setMaximumSize() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.getPageSize();

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.getPageSize() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.getPageSize() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.setPageSize(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setPageSize() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setPageSize() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.compileStatement("SELECT 1;");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.compileStatement() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.compileStatement() did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.query("t1", new String[]{"a", "b"}, null, null, null, null, null);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.query() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.query() did throw exception on closed database OK", e);
            }

            // TODO: cover more query functions

            try {
                // should throw IllegalStateException:
                database.execSQL("SELECT 1;");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.execSQL(String) did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.execSQL(String) did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.execSQL("SELECT 1;", new Object[1]);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.execSQL(String, Object[]) did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.execSQL(String, Object[]) did throw exception on closed database OK", e);
            }

            try {
                // should throw IllegalStateException:
                database.rawExecSQL("SELECT 1;");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.rawExecSQL() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.rawExecSQL() did throw exception on closed database OK", e);
            }

            /* operations that do not explicitly check if db is closed
             * ([should] throw SQLiteException on a closed database): */

            try {
                // should throw IllegalStateException:
                database.setLocale(Locale.getDefault());

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setLocale() did not throw exception on closed database");
                return false;
            } catch (SQLiteException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.setLocale() did throw exception on closed database OK", e);
            }

            try {
                // [should] throw an exception on a closed database:
                database.changePassword("new-password");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.changePassword(String) did not throw exception on closed database");
                return false;
            } catch (SQLiteException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.changePassword(String) did throw exception on closed database OK", e);
            }

            try {
                // [should] throw an exception on a closed database:
                database.changePassword("new-password".toCharArray());

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.changePassword(char []) did not throw exception on closed database");
                return false;
            } catch (SQLiteException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.changePassword(char []) did throw exception on closed database OK", e);
            }

            try {
                // [should] throw an exception on a closed database:
                database.markTableSyncable("aa", "bb");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.markTableSyncable(String, String) did throw exception on closed database");
                return false;
            } catch (SQLiteException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.markTableSyncable(String, String) did throw exception on closed database OK", e);
            }

            /** TODO-BROKEN - SQLiteDatabase.markTableSyncable(String, String, String) does not throw exception on closed database:
            try {
                // [should] throw an exception on a closed database:
                database.markTableSyncable("aa", "bb", "cc");

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.markTableSyncable(String, String, String) did throw exception on closed database");
                return false;
            } catch (SQLiteException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.markTableSyncable(String, String, String) did throw exception on closed database OK", e);
            }
            **/

            try {
                // should throw IllegalStateException [since it calls getVersion()]:
                database.needUpgrade(111);

                // should not get here:
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.needUpgrade() did not throw exception on closed database");
                return false;
            } catch (IllegalStateException e) {
                Log.v(ZeteticApplication.TAG, "SQLiteDatabase.needUpgrade() did throw exception on closed database OK", e);
            }

            /* operations that are NOT expected to throw an exception if the database is closed ([should] not crash) */

            database.setLockingEnabled(false);
            database.setLockingEnabled(true);

            database.yieldIfContended();
            database.yieldIfContendedSafely();
            database.yieldIfContendedSafely(100);

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
                Log.e(ZeteticApplication.TAG, "SQLiteDatabase.setMaxSqlCacheSize() did not throw exception on closed database");
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
