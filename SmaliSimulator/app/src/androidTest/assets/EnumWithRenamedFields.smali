.class public final enum LEnumWithRenamedFields;
.super Ljava/lang/Enum;
.source "EnumWithRenamedFields.java"


# annotations
.annotation system Ldalvik/annotation/Signature;
    value = {
        "Ljava/lang/Enum<",
        "LEnumWithRenamedFields;",
        ">;"
    }
.end annotation


# static fields
.field private static final synthetic $VALUES:[LEnumWithRenamedFields;

.field public static final enum a:LEnumWithRenamedFields;

.field public static final enum b:LEnumWithRenamedFields;

.field public static final enum c:LEnumWithRenamedFields;


# direct methods
.method static constructor <clinit>()V
    .locals 7

    new-instance v0, LEnumWithRenamedFields;

    const-string v1, "State1"

    const/4 v2, 0x0

    invoke-direct {v0, v1, v2}, LEnumWithRenamedFields;-><init>(Ljava/lang/String;I)V

    sput-object v0, LEnumWithRenamedFields;->a:LEnumWithRenamedFields;

    new-instance v1, LEnumWithRenamedFields;

    const-string v3, "State2"

    const/4 v4, 0x1

    invoke-direct {v1, v3, v4}, LEnumWithRenamedFields;-><init>(Ljava/lang/String;I)V

    sput-object v1, LEnumWithRenamedFields;->b:LEnumWithRenamedFields;

    new-instance v3, LEnumWithRenamedFields;

    const-string v5, "State3"

    const/4 v6, 0x2

    invoke-direct {v3, v5, v6}, LEnumWithRenamedFields;-><init>(Ljava/lang/String;I)V

    sput-object v3, LEnumWithRenamedFields;->c:LEnumWithRenamedFields;

    const/4 v5, 0x3

    new-array v5, v5, [LEnumWithRenamedFields;

    aput-object v0, v5, v2

    aput-object v1, v5, v4

    aput-object v3, v5, v6

    sput-object v5, LEnumWithRenamedFields;->$VALUES:[LEnumWithRenamedFields;

    return-void
.end method

.method private constructor <init>(Ljava/lang/String;I)V
    .locals 0

    invoke-direct {p0, p1, p2}, Ljava/lang/Enum;-><init>(Ljava/lang/String;I)V

    return-void
.end method

.method public static testEnumWithRenamedFields()Ljava/lang/String;
    .locals 3

    new-instance v0, Ljava/lang/StringBuilder;

    invoke-direct {v0}, Ljava/lang/StringBuilder;-><init>()V

    invoke-static {}, LEnumWithRenamedFields;->values()[LEnumWithRenamedFields;

    move-result-object v1

    const/4 v2, 0x1

    aget-object v1, v1, v2

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    const-string v2, "\n"

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, LEnumWithRenamedFields;->a:LEnumWithRenamedFields;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, LEnumWithRenamedFields;->b:LEnumWithRenamedFields;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    sget-object v1, LEnumWithRenamedFields;->c:LEnumWithRenamedFields;

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    move-result-object v1

    invoke-virtual {v1, v2}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    const-string v1, "State1"

    invoke-static {v1}, LEnumWithRenamedFields;->valueOf(Ljava/lang/String;)LEnumWithRenamedFields;

    move-result-object v1

    invoke-virtual {v0, v1}, Ljava/lang/StringBuilder;->append(Ljava/lang/Object;)Ljava/lang/StringBuilder;

    invoke-virtual {v0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v1

    return-object v1
.end method

.method public static valueOf(Ljava/lang/String;)LEnumWithRenamedFields;
    .locals 1

    const-class v0, LEnumWithRenamedFields;

    invoke-static {v0, p0}, Ljava/lang/Enum;->valueOf(Ljava/lang/Class;Ljava/lang/String;)Ljava/lang/Enum;

    move-result-object v0

    check-cast v0, LEnumWithRenamedFields;

    return-object v0
.end method

.method public static values()[LEnumWithRenamedFields;
    .locals 1

    sget-object v0, LEnumWithRenamedFields;->$VALUES:[LEnumWithRenamedFields;

    invoke-virtual {v0}, [LEnumWithRenamedFields;->clone()Ljava/lang/Object;

    move-result-object v0

    check-cast v0, [LEnumWithRenamedFields;

    return-object v0
.end method
