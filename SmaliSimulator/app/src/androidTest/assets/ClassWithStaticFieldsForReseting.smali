.class public LClassWithStaticFieldsForReseting;
.super Ljava/lang/Object;
.source "ClassWithStaticFieldsForReseting.java"


# static fields
.field public static f:F

.field public static i:I

.field public static o1:LClassWithStaticFieldsForReseting;

.field public static s:Ljava/lang/String;

.field public static s_array:[Ljava/lang/String;

.field public static s_array2:[[Ljava/lang/String;


# instance fields
.field public ff:F

.field public ii:I

.field public ss:Ljava/lang/String;

.field public ss_array:[Ljava/lang/String;


# direct methods
.method static constructor <clinit>()V
    .locals 5

    const/4 v0, 0x1

    sput v0, LClassWithStaticFieldsForReseting;->i:I

    const-string v1, "Hello"

    sput-object v1, LClassWithStaticFieldsForReseting;->s:Ljava/lang/String;

    const-string v1, "A"

    const-string v2, "B"

    const-string v3, "C"

    filled-new-array {v1, v2, v3}, [Ljava/lang/String;

    move-result-object v1

    sput-object v1, LClassWithStaticFieldsForReseting;->s_array:[Ljava/lang/String;

    const/4 v1, 0x2

    new-array v1, v1, [[Ljava/lang/String;

    const-string v2, "a"

    const-string v3, "b"

    const-string v4, "c"

    filled-new-array {v2, v3, v4}, [Ljava/lang/String;

    move-result-object v2

    const/4 v3, 0x0

    aput-object v2, v1, v3

    const-string v2, "e"

    const-string v3, "f"

    const-string v4, "g"

    filled-new-array {v2, v3, v4}, [Ljava/lang/String;

    move-result-object v2

    aput-object v2, v1, v0

    sput-object v1, LClassWithStaticFieldsForReseting;->s_array2:[[Ljava/lang/String;

    new-instance v0, LClassWithStaticFieldsForReseting;

    invoke-direct {v0}, LClassWithStaticFieldsForReseting;-><init>()V

    sput-object v0, LClassWithStaticFieldsForReseting;->o1:LClassWithStaticFieldsForReseting;

    return-void
.end method

.method public constructor <init>()V
    .locals 3

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    const/16 v0, 0x17

    iput v0, p0, LClassWithStaticFieldsForReseting;->ii:I

    const-string v0, "Hello Instance"

    iput-object v0, p0, LClassWithStaticFieldsForReseting;->ss:Ljava/lang/String;

    const v0, 0x42a9147b

    iput v0, p0, LClassWithStaticFieldsForReseting;->ff:F

    const-string v0, "Q"

    const-string v1, "W"

    const-string v2, "E"

    filled-new-array {v0, v1, v2}, [Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, LClassWithStaticFieldsForReseting;->ss_array:[Ljava/lang/String;

    return-void
.end method

.method public static changeValues()V
    .locals 6

    const/4 v0, 0x2

    sput v0, LClassWithStaticFieldsForReseting;->i:I

    const-string v1, "Bye"

    sput-object v1, LClassWithStaticFieldsForReseting;->s:Ljava/lang/String;

    const v1, 0x41bb3333

    sput v1, LClassWithStaticFieldsForReseting;->f:F

    sget-object v1, LClassWithStaticFieldsForReseting;->s_array:[Ljava/lang/String;

    const-string v2, "K"

    const/4 v3, 0x1

    aput-object v2, v1, v3

    sget-object v1, LClassWithStaticFieldsForReseting;->s_array2:[[Ljava/lang/String;

    const/4 v2, 0x0

    aget-object v4, v1, v2

    const-string v5, "m"

    aput-object v5, v4, v3

    aget-object v1, v1, v3

    const-string v3, "n"

    aput-object v3, v1, v0

    sget-object v0, LClassWithStaticFieldsForReseting;->o1:LClassWithStaticFieldsForReseting;

    const/16 v1, 0x2d

    iput v1, v0, LClassWithStaticFieldsForReseting;->ii:I

    const-string v1, "Bye Instance"

    iput-object v1, v0, LClassWithStaticFieldsForReseting;->ss:Ljava/lang/String;

    const v1, 0x42c43d71

    iput v1, v0, LClassWithStaticFieldsForReseting;->ff:F

    iget-object v0, v0, LClassWithStaticFieldsForReseting;->ss_array:[Ljava/lang/String;

    const-string v1, "P"

    aput-object v1, v0, v2

    return-void
.end method