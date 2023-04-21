//package com.example.sqlitedemo.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.DatabaseErrorHandler;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.example.sqlitedemo.MainActivity;
//import com.example.sqlitedemo.model.Category;
//import com.example.sqlitedemo.model.Item;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Database extends SQLiteOpenHelper {
//    private final static String DATABASE_NAME="nhom7.db";
//    private static int DATABASE_VERSION = 1;
//
//    public Database(@Nullable Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        //tat ca cac bang tao o day, tao bang quan he 1 truoc
//        String sql="create table categories("+
//                "id integer primary key autoincrement,"+
//                "name text)";
//        db.execSQL(sql);
//        //tao items
//        sql="create table items("+
//                "id integer primary key autoincrement," +
//                "name text,"+
//                "cid integer,"+
//                "price real,"+
//                "dateUdate text," +
//                "foreign key(cid) references categories(id))";
//        db.execSQL(sql);
//        //cac bang nua neu co
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//
//    }
//
//    //insert categories
//    public void insertCate(Category c){
//        String sql="insert into categories(name)"+"values(?)";
//        String[] args={c.getName()};
//        SQLiteDatabase st= getWritableDatabase();
//        st.execSQL(sql,args);
//        st.close();
//    }
//    //insert items
//    public long insertItem(Item t){
//        ContentValues values= new ContentValues();
//        values.put("name",t.getName());
//        values.put("cid",t.getCategory().getId());
//        values.put("price",t.getPrice());
//        values.put("dateUpdate", t.getDateUpdate());
//        SQLiteDatabase st= getWritableDatabase();
//        return st.insert("items",null,values);
//    }
//    //lay ca bang categories
//    public List<Category> getCates(){
//        List<Category> list = new ArrayList<>();
//        SQLiteDatabase st = getReadableDatabase();
//        Cursor rs = st.query("categories", null, null,
//                null, null, null, null);
//        while ((rs!=null && rs.moveToNext())){
//            list.add(new Category(rs.getInt(0), rs.getString(1)));
//        }
//        st.close();
//        return list;
//    }
//    public List<Item> getItems(){
//        List<Item> list = new ArrayList<>();
//        String sql= "select t.id,t.name,t.price,t.dateUpdate,c.id,c.name " +
//                "from categories c inner join items t on (c.id=t.cid)" +
//                "order by dateUpdate desc";
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs = st.rawQuery(sql,null);
//        while ( rs!=null && rs.moveToNext()){
//            Category c = new Category(rs.getInt(4),rs.getString(5));
//            Item t = new Item(rs.getInt(0), rs.getString(1), rs.getDouble(2),rs.getString(3),c);
//            list.add(t);
//        }
//        rs.close();
//        return list;
//    }
//
//    //lay theo id
//    public Category getCateById(int id) {
//        String where = "id=?";
//        String[] agrs = {Integer.toString(id)};
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs = st.query("catogories", null, where,
//                agrs, null, null, null);
//        if (rs != null && rs.moveToNext()) {
//            return new Category(rs.getInt(0),rs.getString(1));
//        }
//        return null;
//    }
//
//    public Item getItemById(int id) {
//        String where = "id=?";
//        String[] agrs = {Integer.toString(id)};
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs = st.query("items", null, where,
//                agrs, null, null, null);
//        if (rs != null && rs.moveToNext()) {
//            return new Item(rs.getInt(0), rs.getString(1), rs.getDouble(3),
//                    rs.getString(4), new Category(rs.getInt(2), ""));
//        }
//        return null;
//    }
//
//    //update
//    public int update(Item t) {
//        ContentValues values=new ContentValues();
//        values.put("name",t.getName());
//        values.put("cid",t.getCategory().getId());
//        values.put("price", t.getPrice());
//        values.put("dateUpdate", t.getDateUpdate());
//        String where = "id=?";
//        String[] args = {Integer.toString(t.getId())};
//        SQLiteDatabase st=getWritableDatabase();
//        return st.update("items", values, where, args);
//    }
//
//    //delete
//    public void delete(int[] id) {
////        String where = "id=?";
////        String[] agrs = {Integer.toString(id)};
////        SQLiteDatabase st = getWritableDatabase();
////        return st.delete("items", where, agrs);
//        /**/
//        String sql = "delete from items where id in (";
//        for (int i=0;i<id.length-1;i++){
//            sql+=id[i]+",";
//        }
//        sql+=id[id.length-1]+")";
//        SQLiteDatabase st = getWritableDatabase();
//        st.execSQL(sql,null);
//         /**/
//    }
//    //tim kiem theo by key
//    public List<Item> searchByKey(String key){
//        List<Item> list = new ArrayList<>();
//        String sql= "select t.id,t.name,t.price,t.dateUpdate,c.id,c.name " +
//                "from categories c inner join items t on (c.id=t.cid)" +
//                "where t.name like ? or c.name like ? order by dateUpdate desc";
//        String[] agrs={"%"+key+"%", "%"+key+"%"};
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs = st.rawQuery(sql,agrs);
//        while ( rs!=null && rs.moveToNext()){
//            Category c = new Category(rs.getInt(4),rs.getString(5));
//            Item t = new Item(rs.getInt(0), rs.getString(1), rs.getDouble(2),rs.getString(3),c);
//            list.add(t);
//        }
//        rs.close();
//        return list;
//    }
//    //tim kiem gia tri so
//
//    //tim kiem theo from....to
//    public List<Item> searchByPriceFromTo(double from, double to) {
//        List<Item> list = new ArrayList<>();
//        String where = "between ? and ?";
//        String[] agrs = {Double.toString(from),Double.toString(to)};
//        SQLiteDatabase st=getReadableDatabase();
//        Cursor rs = st.query("items", null, where,
//                agrs, null, null, null);
//        while (rs != null && rs.moveToNext()) {
//            list.add(new Item(rs.getInt(0), rs.getString(1), rs.getDouble(3),
//                    rs.getString(4), new Category(rs.getInt(2), "")));
//        }
//        return list;
//    }
//    //thong ke
//}