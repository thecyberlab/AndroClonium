.class public LMyDatabaseHelper;
.super Landroid/database/sqlite/SQLiteOpenHelper;
.source "MyDatabaseHelper.java"


# static fields
.field public static final DATABASE_NAME:Ljava/lang/String; = "CloudDBCache.db"

.field public static final DATABASE_VERSION:I = 0x3

.field private static final SQL_CREATE_TABLE:Ljava/lang/String; = "CREATE TABLE table1 ( id  INTEGER PRIMARY KEY, myKey  TEXT,value TEXT)"

.field private static final SQL_DROP_TABLE:Ljava/lang/String; = "DROP TABLE IF EXISTS table1"

.field private static myDatabaseHelper:LMyDatabaseHelper;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 18
    const/4 v0, 0x0

    sput-object v0, LMyDatabaseHelper;->myDatabaseHelper:LMyDatabaseHelper;

    return-void
.end method

.method private constructor <init>(Landroid/content/Context;)V
    .locals 3
    .param p1, "context"    # Landroid/content/Context;

    .line 28
    const-string v0, "CloudDBCache.db"

    const/4 v1, 0x0

    const/4 v2, 0x3

    invoke-direct {p0, p1, v0, v1, v2}, Landroid/database/sqlite/SQLiteOpenHelper;-><init>(Landroid/content/Context;Ljava/lang/String;Landroid/database/sqlite/SQLiteDatabase$CursorFactory;I)V

    .line 29
    return-void
.end method

.method public static getInstance(Landroid/content/Context;)LMyDatabaseHelper;
    .locals 1
    .param p0, "context"    # Landroid/content/Context;

    .line 21
    sget-object v0, LMyDatabaseHelper;->myDatabaseHelper:LMyDatabaseHelper;

    if-nez v0, :cond_0

    .line 22
    new-instance v0, LMyDatabaseHelper;

    invoke-direct {v0, p0}, LMyDatabaseHelper;-><init>(Landroid/content/Context;)V

    sput-object v0, LMyDatabaseHelper;->myDatabaseHelper:LMyDatabaseHelper;

    .line 24
    :cond_0
    sget-object v0, LMyDatabaseHelper;->myDatabaseHelper:LMyDatabaseHelper;

    return-object v0
.end method

.method public static test(Landroid/content/Context;)LMyDatabaseHelper;
.locals 1

new-instance v0, LMyDatabaseHelper;

invoke-direct {v0, p0}, LMyDatabaseHelper;-><init>(Landroid/content/Context;)V

return-object v0
.end method


# virtual methods
.method public onCreate(Landroid/database/sqlite/SQLiteDatabase;)V
    .locals 1
    .param p1, "db"    # Landroid/database/sqlite/SQLiteDatabase;

    .line 33
    const-string v0, "CREATE TABLE table1 ( id  INTEGER PRIMARY KEY, myKey  TEXT,value TEXT)"

    invoke-virtual {p1, v0}, Landroid/database/sqlite/SQLiteDatabase;->execSQL(Ljava/lang/String;)V

    .line 34
    return-void
.end method

.method public onUpgrade(Landroid/database/sqlite/SQLiteDatabase;II)V
    .locals 1
    .param p1, "db"    # Landroid/database/sqlite/SQLiteDatabase;
    .param p2, "oldVersion"    # I
    .param p3, "newVersion"    # I

    .line 38
    const-string v0, "DROP TABLE IF EXISTS table1"

    invoke-virtual {p1, v0}, Landroid/database/sqlite/SQLiteDatabase;->execSQL(Ljava/lang/String;)V

    .line 39
    invoke-virtual {p0, p1}, LMyDatabaseHelper;->onCreate(Landroid/database/sqlite/SQLiteDatabase;)V

    .line 40
    return-void
.end method
