.class public Lstarcom/debug/LoggingSystem;
.super Ljava/lang/Object;
.source "LoggingSystem.java"


# static fields
.field public static L_ALL:Ljava/util/logging/Level;

.field public static L_CONFIG:Ljava/util/logging/Level;

.field public static L_FINE:Ljava/util/logging/Level;

.field public static L_FINER:Ljava/util/logging/Level;

.field public static L_FINEST:Ljava/util/logging/Level;

.field public static L_INFO:Ljava/util/logging/Level;

.field public static L_OFF:Ljava/util/logging/Level;

.field public static L_SEVERE:Ljava/util/logging/Level;

.field public static L_WARNING:Ljava/util/logging/Level;


# direct methods
.method static constructor <clinit>()V
    .locals 1

    .prologue
    .line 8
    sget-object v0, Ljava/util/logging/Level;->ALL:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_ALL:Ljava/util/logging/Level;

    .line 9
    sget-object v0, Ljava/util/logging/Level;->OFF:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_OFF:Ljava/util/logging/Level;

    .line 10
    sget-object v0, Ljava/util/logging/Level;->SEVERE:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_SEVERE:Ljava/util/logging/Level;

    .line 11
    sget-object v0, Ljava/util/logging/Level;->WARNING:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_WARNING:Ljava/util/logging/Level;

    .line 12
    sget-object v0, Ljava/util/logging/Level;->INFO:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_INFO:Ljava/util/logging/Level;

    .line 13
    sget-object v0, Ljava/util/logging/Level;->CONFIG:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_CONFIG:Ljava/util/logging/Level;

    .line 14
    sget-object v0, Ljava/util/logging/Level;->FINE:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_FINE:Ljava/util/logging/Level;

    .line 15
    sget-object v0, Ljava/util/logging/Level;->FINER:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_FINER:Ljava/util/logging/Level;

    .line 16
    sget-object v0, Ljava/util/logging/Level;->FINEST:Ljava/util/logging/Level;

    sput-object v0, Lstarcom/debug/LoggingSystem;->L_FINEST:Ljava/util/logging/Level;

    return-void
.end method

.method public constructor <init>()V
    .locals 0

    .prologue
    .line 6
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method private static getName(Ljava/lang/Class;)Ljava/lang/String;
    .locals 1
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class",
            "<+",
            "Ljava/lang/Object;",
            ">;)",
            "Ljava/lang/String;"
        }
    .end annotation

    .prologue
    .line 57
    .local p0, "aclass":Ljava/lang/Class;, "Ljava/lang/Class<+Ljava/lang/Object;>;"
    invoke-virtual {p0}, Ljava/lang/Class;->getCanonicalName()Ljava/lang/String;

    move-result-object v0

    .line 58
    .local v0, "name":Ljava/lang/String;
    invoke-virtual {v0}, Ljava/lang/Object;->getClass()Ljava/lang/Class;

    .line 59
    return-object v0
.end method

.method public static varargs info(Ljava/lang/Class;[Ljava/lang/String;)V
    .locals 2
    .param p1, "msg"    # [Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class",
            "<+",
            "Ljava/lang/Object;",
            ">;[",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .prologue
    .line 30
    .local p0, "aclass":Ljava/lang/Class;, "Ljava/lang/Class<+Ljava/lang/Object;>;"
    sget-object v0, Ljava/util/logging/Level;->INFO:Ljava/util/logging/Level;

    invoke-static {p1}, Lstarcom/debug/LoggingSystem;->join([Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, p0, v1}, Lstarcom/debug/LoggingSystem;->log(Ljava/util/logging/Level;Ljava/lang/Class;Ljava/lang/String;)V

    return-void
.end method

.method private static join([Ljava/lang/String;)Ljava/lang/String;
    .locals 5
    .param p0, "msg"    # [Ljava/lang/String;

    .prologue
    const/4 v2, 0x0

    .line 46
    array-length v3, p0

    const/4 v4, 0x1

    if-ne v3, v4, :cond_0

    aget-object v2, p0, v2

    .line 52
    :goto_0
    return-object v2

    .line 47
    :cond_0
    new-instance v1, Ljava/lang/StringBuilder;

    invoke-direct {v1}, Ljava/lang/StringBuilder;-><init>()V

    .line 48
    .local v1, "sb":Ljava/lang/StringBuilder;
    array-length v3, p0

    :goto_1
    if-ge v2, v3, :cond_1

    aget-object v0, p0, v2

    .line 50
    .local v0, "curMsg":Ljava/lang/String;
    invoke-virtual {v1, v0}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 48
    add-int/lit8 v2, v2, 0x1

    goto :goto_1

    .line 52
    .end local v0    # "curMsg":Ljava/lang/String;
    :cond_1
    invoke-virtual {v1}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v2

    goto :goto_0
.end method

.method private static log(Ljava/util/logging/Level;Ljava/lang/Class;Ljava/lang/String;)V
    .locals 3
    .param p0, "level"    # Ljava/util/logging/Level;
    .param p2, "msg"    # Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/util/logging/Level;",
            "Ljava/lang/Class",
            "<+",
            "Ljava/lang/Object;",
            ">;",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .prologue
    .line 40
    .local p1, "aclass":Ljava/lang/Class;, "Ljava/lang/Class<+Ljava/lang/Object;>;"
    invoke-static {p1}, Lstarcom/debug/LoggingSystem;->getName(Ljava/lang/Class;)Ljava/lang/String;

    move-result-object v0

    .line 41
    .local v0, "name":Ljava/lang/String;
    invoke-static {v0}, Ljava/util/logging/Logger;->getLogger(Ljava/lang/String;)Ljava/util/logging/Logger;

    move-result-object v1

    const-string/jumbo v2, ""

    invoke-virtual {v1, p0, v0, v2, p2}, Ljava/util/logging/Logger;->logp(Ljava/util/logging/Level;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V

    .line 42
    return-void
.end method

.method public static varargs severe(Ljava/lang/Class;Ljava/lang/Exception;[Ljava/lang/String;)V
    .locals 3
    .param p1, "e"    # Ljava/lang/Exception;
    .param p2, "msg"    # [Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class",
            "<+",
            "Ljava/lang/Object;",
            ">;",
            "Ljava/lang/Exception;",
            "[",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .prologue
    .line 34
    .local p0, "aclass":Ljava/lang/Class;, "Ljava/lang/Class<+Ljava/lang/Object;>;"
    invoke-virtual {p1}, Ljava/lang/Exception;->printStackTrace()V

    const/4 v0, 0x1

    new-array v0, v0, [Ljava/lang/String;

    const/4 v1, 0x0

    invoke-virtual {p0}, Ljava/lang/Class;->toString()Ljava/lang/String;

    move-result-object v2

    aput-object v2, v0, v1

    invoke-static {p0, v0}, Lstarcom/debug/LoggingSystem;->severe(Ljava/lang/Class;[Ljava/lang/String;)V

    return-void
.end method

.method public static varargs severe(Ljava/lang/Class;[Ljava/lang/String;)V
    .locals 2
    .param p1, "msg"    # [Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class",
            "<+",
            "Ljava/lang/Object;",
            ">;[",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .prologue
    .line 32
    .local p0, "aclass":Ljava/lang/Class;, "Ljava/lang/Class<+Ljava/lang/Object;>;"
    sget-object v0, Ljava/util/logging/Level;->SEVERE:Ljava/util/logging/Level;

    invoke-static {p1}, Lstarcom/debug/LoggingSystem;->join([Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, p0, v1}, Lstarcom/debug/LoggingSystem;->log(Ljava/util/logging/Level;Ljava/lang/Class;Ljava/lang/String;)V

    return-void
.end method

.method public static varargs warn(Ljava/lang/Class;[Ljava/lang/String;)V
    .locals 2
    .param p1, "msg"    # [Ljava/lang/String;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Ljava/lang/Class",
            "<+",
            "Ljava/lang/Object;",
            ">;[",
            "Ljava/lang/String;",
            ")V"
        }
    .end annotation

    .prologue
    .line 31
    .local p0, "aclass":Ljava/lang/Class;, "Ljava/lang/Class<+Ljava/lang/Object;>;"
    sget-object v0, Ljava/util/logging/Level;->WARNING:Ljava/util/logging/Level;

    invoke-static {p1}, Lstarcom/debug/LoggingSystem;->join([Ljava/lang/String;)Ljava/lang/String;

    move-result-object v1

    invoke-static {v0, p0, v1}, Lstarcom/debug/LoggingSystem;->log(Ljava/util/logging/Level;Ljava/lang/Class;Ljava/lang/String;)V

    return-void
.end method
