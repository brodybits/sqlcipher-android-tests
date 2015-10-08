package net.zetetic.tests;

import android.database.Cursor;
import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteStatement;

public class UnicodeTest extends SQLCipherTest {
    @Override
    public boolean execute(SQLiteDatabase database) {
        String expected = "КАКОЙ-ТО КИРИЛЛИЧЕСКИЙ ТЕКСТ"; // SOME Cyrillic TEXT
        String actual = "";

        Cursor result = database.rawQuery("select UPPER('Какой-то кириллический текст') as u1", new String[]{});
        if (result != null) {
            result.moveToFirst();
            actual = result.getString(0);
            result.close();
        }
        if (!actual.equals(expected)) return false;

        // FUTURE TBD only run this on newer versions of Android once the following bug fixed:
        // https://code.google.com/p/android/issues/detail?id=81341
        if (false) {
            // This will crash on all Android releases 1.X-5.X due Android bug 81341:
            SQLiteStatement st = database.compileStatement("SELECT '\uD83D\uDE03'"); // SMILING FACE (MOUTH OPEN)
            String res = st.simpleQueryForString();
            if (!res.equals("\uD83D\uDE03")) return false;
        }

        // all ok:
        return true;
    }

    @Override
    public String getName() {
        return "Unicode (ICU) Test";
    }
}
