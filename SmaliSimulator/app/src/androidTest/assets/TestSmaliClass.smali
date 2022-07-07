.class public LTestSmaliClass;
.super LTestSmaliClassParent;
.source "TestSmaliClass.java"

# interfaces
.implements LTestInterface;


# static fields
.field protected static i2:I

.field protected static i4:I

.field protected static s2:Ljava/lang/String;

.field public static final i3:I = 0x10

.field public static final b1:Z = true

.field public static b2:Z

.field public static final s4:Ljava/lang/String; = "Hello from child final"

.field public static s5:Ljava/lang/String;


# instance fields
.field protected i1:I

.field protected s1:Ljava/lang/String;

.field protected s3:Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .line 6
    const/4 v0, 0x6

    sput v0, LTestSmaliClass;->i2:I

    .line 8
    const-string v0, "Hello from child static"

    sput-object v0, LTestSmaliClass;->s2:Ljava/lang/String;

    return-void
.end method

.method public constructor <init>()V
    .locals 1

    .line 4
    invoke-direct {p0}, LTestSmaliClassParent;-><init>()V

    .line 9
    const-string v0, "Hello from child instance"

    iput-object v0, p0, LTestSmaliClass;->s3:Ljava/lang/String;

    return-void
.end method


.method public static test()I
    .locals 1

    const/4 v0, 0x6

    return v0
.end method
