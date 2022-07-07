.class public LClassWithConstructorSelfObjectAliasChild;
.super LClassWithConstructorSelfObjectAliasParent;
.source "ClassWithConstructorSelfObjectAliasChild.java"


# direct methods
.method constructor <init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;IIIILjava/lang/Object;Ljava/lang/Object;)V
    .locals 12

    move-object v11, p0

    const/4 v2, 0x0

    const/4 v7, 0x0

    const/4 v10, 0x0

    move-object v0, v11

    move-object v1, p1

    move-object v3, p2

    move/from16 v4, p6

    move/from16 v5, p7

    move/from16 v6, p5

    move-object/from16 v8, p9

    move-object/from16 v9, p8

    invoke-direct/range {v0 .. v10}, LClassWithConstructorSelfObjectAliasParent;-><init>(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;IIILjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Z)V

    .line 9
    return-void
.end method
