.class public LInstanceTest;
.super Ljava/lang/Object;
.source "InstanceTest.java"


# instance fields
.field public a1:[I

.field public a2:[Ljava/lang/Object;

.field public b1:B

.field public c1:C

.field public d1:D

.field public f1:F

.field public i1:I

.field i2:I

.field protected i3:I

.field private i4:I

.field public l1:J

.field public o1:Ljava/lang/Object;

.field public s1:S

.field public z1:Z


# direct methods
.method public constructor <init>()V
    .locals 0

    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

#------------
.method public static get_i1()V
    .locals 2

    iget v1, v0, LInstanceTest;->i1:I

    return-void
.end method

.method public static put_i1()V
    .locals 2

    iput v1, v0, LInstanceTest;->i1:I

    return-void
.end method

.method public static get_f1()V
    .locals 2

    iget v1, v0, LInstanceTest;->f1:F

    return-void
.end method

.method public static put_f1()V
    .locals 2

    iput v1, v0, LInstanceTest;->f1:F

    return-void
.end method

.method public static getWide_l1()V
    .locals 2

    iget-wide v1, v0, LInstanceTest;->l1:J

    return-void
.end method

.method public static putWide_l1()V
   .locals 2

   iput-wide v1, v0, LInstanceTest;->l1:J

   return-void
.end method

.method public static getWide_d1()V
    .locals 2

    iget-wide v1, v0, LInstanceTest;->d1:D

    return-void
.end method

.method public static putWide_d1()V
   .locals 2

   iput-wide v1, v0, LInstanceTest;->d1:D

   return-void
.end method


.method public static getBoolean_z1()V
    .locals 2

    iget-boolean v1, v0, LInstanceTest;->z1:Z

    return-void
.end method

.method public static putBoolean_z1()V
    .locals 2

    iput-boolean v1, v0, LInstanceTest;->z1:Z

    return-void
.end method

.method public static getByte_b1()V
    .locals 2

    iget-byte v1, v0, LInstanceTest;->b1:B

    return-void
.end method

.method public static putByte_b1()V
    .locals 2

    iput-byte v1, v0, LInstanceTest;->b1:B

    return-void
.end method

.method public static getChar_c1()V
    .locals 2

    iget-char v1, v0, LInstanceTest;->c1:C

    return-void
.end method

.method public static putChar_c1()V
    .locals 2

    iput-char v1, v0, LInstanceTest;->c1:C

    return-void
.end method

.method public static getShort_s1()V
    .locals 2

    iget-short v1, v0, LInstanceTest;->s1:S

    return-void
.end method

.method public static putShort_s1()V
    .locals 2

    iput-short v1, v0, LInstanceTest;->s1:S

    return-void
.end method

.method public static getObject_o1()V
    .locals 2

    iget-object v1, v0, LInstanceTest;->o1:Ljava/lang/Object;

   return-void
.end method


.method public static putObject_o1()V
   .locals 2

   iput-object v1, v0, LInstanceTest;->o1:Ljava/lang/Object;

   return-void
.end method

.method public static getObject_a1()V
    .locals 2

    iget-object v1, v0, LInstanceTest;->a1:[I

   return-void
.end method


.method public static putObject_a1()V
   .locals 2

   iput-object v1, v0, LInstanceTest;->a1:[I

   return-void
.end method


.method public static getObject_a2()V
    .locals 2

    iget-object v1, v0, LInstanceTest;->a2:[Ljava/lang/Object;

   return-void
.end method


.method public static putObject_a2()V
   .locals 2

   iput-object v1, v0, LInstanceTest;->a2:[Ljava/lang/Object;

   return-void
.end method

#.method public static putObjects()V
 #   .locals 3

  #  iput-object v2, v0, v1
   # iput-object v4, v0, v3

    #return-void
#.end method



