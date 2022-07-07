.class public Lstarcom/snd/util/Resources;
.super Ljava/lang/Object;
.source "Resources.java"


# direct methods
.method public constructor <init>()V
    .locals 0

    .prologue
    .line 11
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method

.method public static readTextRaw(Landroid/content/Context;I)Ljava/lang/String;
    .locals 14
    .param p0, "context"    # Landroid/content/Context;
    .param p1, "rawID"    # I

    .prologue
    const/4 v12, 0x1

    const/4 v11, 0x0

    const/4 v8, 0x0

    .line 17
    new-instance v5, Ljava/lang/StringBuilder;

    invoke-direct {v5}, Ljava/lang/StringBuilder;-><init>()V

    .line 18
    .local v5, "sb":Ljava/lang/StringBuilder;
    const-class v6, Lstarcom/snd/util/Resources;

    new-array v7, v12, [Ljava/lang/String;

    const-string/jumbo v9, "Read raw file!"

    aput-object v9, v7, v11

    invoke-static {v6, v7}, Lstarcom/debug/LoggingSystem;->info(Ljava/lang/Class;[Ljava/lang/String;)V

    .line 19
    :try_start_0
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v6

    invoke-virtual {v6, p1}, Landroid/content/res/Resources;->openRawResource(I)Ljava/io/InputStream;
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_3

    move-result-object v2

    .local v2, "is":Ljava/io/InputStream;
    const/4 v6, 0x0

    .line 20
    :try_start_1
    new-instance v3, Ljava/io/InputStreamReader;

    const-string/jumbo v7, "UTF-8"

    invoke-direct {v3, v2, v7}, Ljava/io/InputStreamReader;-><init>(Ljava/io/InputStream;Ljava/lang/String;)V
    :try_end_1
    .catch Ljava/lang/Throwable; {:try_start_1 .. :try_end_1} :catch_2
    .catchall {:try_start_1 .. :try_end_1} :catchall_4

    .line 19
    .local v3, "isr":Ljava/io/InputStreamReader;
    const/4 v7, 0x0

    .line 21
    :try_start_2
    new-instance v0, Ljava/io/BufferedReader;

    invoke-direct {v0, v3}, Ljava/io/BufferedReader;-><init>(Ljava/io/Reader;)V
    :try_end_2
    .catch Ljava/lang/Throwable; {:try_start_2 .. :try_end_2} :catch_1
    .catchall {:try_start_2 .. :try_end_2} :catchall_3

    .line 19
    .local v0, "br":Ljava/io/BufferedReader;
    const/4 v9, 0x0

    .line 25
    :goto_0
    :try_start_3
    invoke-virtual {v0}, Ljava/io/BufferedReader;->readLine()Ljava/lang/String;
    :try_end_3
    .catch Ljava/lang/Throwable; {:try_start_3 .. :try_end_3} :catch_0
    .catchall {:try_start_3 .. :try_end_3} :catchall_5

    move-result-object v4

    .line 26
    .local v4, "line":Ljava/lang/String;
    if-nez v4, :cond_3

    .line 30
    if-eqz v0, :cond_0

    if-eqz v8, :cond_7

    :try_start_4
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V
    :try_end_4
    .catch Ljava/lang/Throwable; {:try_start_4 .. :try_end_4} :catch_4
    .catchall {:try_start_4 .. :try_end_4} :catchall_3

    :cond_0
    :goto_1
    if-eqz v3, :cond_1

    if-eqz v8, :cond_9

    :try_start_5
    invoke-virtual {v3}, Ljava/io/InputStreamReader;->close()V
    :try_end_5
    .catch Ljava/lang/Throwable; {:try_start_5 .. :try_end_5} :catch_6
    .catchall {:try_start_5 .. :try_end_5} :catchall_4

    :cond_1
    :goto_2
    if-eqz v2, :cond_2

    if-eqz v8, :cond_b

    :try_start_6
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V
    :try_end_6
    .catch Ljava/lang/Throwable; {:try_start_6 .. :try_end_6} :catch_8
    .catch Ljava/io/IOException; {:try_start_6 .. :try_end_6} :catch_3

    .line 35
    .end local v0    # "br":Ljava/io/BufferedReader;
    .end local v2    # "is":Ljava/io/InputStream;
    .end local v3    # "isr":Ljava/io/InputStreamReader;
    .end local v4    # "line":Ljava/lang/String;
    :cond_2
    :goto_3
    invoke-virtual {v5}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v6

    return-object v6

    .line 27
    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v2    # "is":Ljava/io/InputStream;
    .restart local v3    # "isr":Ljava/io/InputStreamReader;
    .restart local v4    # "line":Ljava/lang/String;
    :cond_3
    :try_start_7
    invoke-virtual {v5, v4}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 28
    const-string/jumbo v10, "\n"

    invoke-virtual {v5, v10}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;
    :try_end_7
    .catch Ljava/lang/Throwable; {:try_start_7 .. :try_end_7} :catch_0
    .catchall {:try_start_7 .. :try_end_7} :catchall_5

    goto :goto_0

    .line 19
    .end local v4    # "line":Ljava/lang/String;
    :catch_0
    move-exception v6

    :try_start_8
    throw v6
    :try_end_8
    .catchall {:try_start_8 .. :try_end_8} :catchall_0

    .line 30
    :catchall_0
    move-exception v7

    move-object v13, v7

    move-object v7, v6

    move-object v6, v13

    :goto_4
    if-eqz v0, :cond_4

    if-eqz v7, :cond_8

    :try_start_9
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V
    :try_end_9
    .catch Ljava/lang/Throwable; {:try_start_9 .. :try_end_9} :catch_5
    .catchall {:try_start_9 .. :try_end_9} :catchall_3

    :cond_4
    :goto_5
    :try_start_a
    throw v6
    :try_end_a
    .catch Ljava/lang/Throwable; {:try_start_a .. :try_end_a} :catch_1
    .catchall {:try_start_a .. :try_end_a} :catchall_3

    .line 19
    .end local v0    # "br":Ljava/io/BufferedReader;
    :catch_1
    move-exception v6

    :try_start_b
    throw v6
    :try_end_b
    .catchall {:try_start_b .. :try_end_b} :catchall_1

    .line 30
    :catchall_1
    move-exception v7

    move-object v13, v7

    move-object v7, v6

    move-object v6, v13

    :goto_6
    if-eqz v3, :cond_5

    if-eqz v7, :cond_a

    :try_start_c
    invoke-virtual {v3}, Ljava/io/InputStreamReader;->close()V
    :try_end_c
    .catch Ljava/lang/Throwable; {:try_start_c .. :try_end_c} :catch_7
    .catchall {:try_start_c .. :try_end_c} :catchall_4

    :cond_5
    :goto_7
    :try_start_d
    throw v6
    :try_end_d
    .catch Ljava/lang/Throwable; {:try_start_d .. :try_end_d} :catch_2
    .catchall {:try_start_d .. :try_end_d} :catchall_4

    .line 19
    .end local v3    # "isr":Ljava/io/InputStreamReader;
    :catch_2
    move-exception v6

    :try_start_e
    throw v6
    :try_end_e
    .catchall {:try_start_e .. :try_end_e} :catchall_2

    .line 30
    :catchall_2
    move-exception v7

    move-object v8, v6

    move-object v6, v7

    :goto_8
    if-eqz v2, :cond_6

    if-eqz v8, :cond_c

    :try_start_f
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V
    :try_end_f
    .catch Ljava/lang/Throwable; {:try_start_f .. :try_end_f} :catch_9
    .catch Ljava/io/IOException; {:try_start_f .. :try_end_f} :catch_3

    :cond_6
    :goto_9
    :try_start_10
    throw v6
    :try_end_10
    .catch Ljava/io/IOException; {:try_start_10 .. :try_end_10} :catch_3

    .line 31
    .end local v2    # "is":Ljava/io/InputStream;
    :catch_3
    move-exception v1

    .line 33
    .local v1, "e":Ljava/io/IOException;
    const-class v6, Lstarcom/snd/util/Resources;

    new-array v7, v12, [Ljava/lang/String;

    const-string/jumbo v8, "Reading raw text"

    aput-object v8, v7, v11

    invoke-static {v6, v1, v7}, Lstarcom/debug/LoggingSystem;->severe(Ljava/lang/Class;Ljava/lang/Exception;[Ljava/lang/String;)V

    goto :goto_3

    .line 30
    .end local v1    # "e":Ljava/io/IOException;
    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v2    # "is":Ljava/io/InputStream;
    .restart local v3    # "isr":Ljava/io/InputStreamReader;
    .restart local v4    # "line":Ljava/lang/String;
    :catch_4
    move-exception v10

    :try_start_11
    invoke-virtual {v9, v10}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_1

    .end local v0    # "br":Ljava/io/BufferedReader;
    .end local v4    # "line":Ljava/lang/String;
    :catchall_3
    move-exception v6

    move-object v7, v8

    goto :goto_6

    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v4    # "line":Ljava/lang/String;
    :cond_7
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V

    goto :goto_1

    .end local v4    # "line":Ljava/lang/String;
    :catch_5
    move-exception v9

    invoke-virtual {v7, v9}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_5

    :cond_8
    invoke-virtual {v0}, Ljava/io/BufferedReader;->close()V
    :try_end_11
    .catch Ljava/lang/Throwable; {:try_start_11 .. :try_end_11} :catch_1
    .catchall {:try_start_11 .. :try_end_11} :catchall_3

    goto :goto_5

    .restart local v4    # "line":Ljava/lang/String;
    :catch_6
    move-exception v9

    :try_start_12
    invoke-virtual {v7, v9}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_2

    .end local v0    # "br":Ljava/io/BufferedReader;
    .end local v3    # "isr":Ljava/io/InputStreamReader;
    .end local v4    # "line":Ljava/lang/String;
    :catchall_4
    move-exception v6

    goto :goto_8

    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v3    # "isr":Ljava/io/InputStreamReader;
    .restart local v4    # "line":Ljava/lang/String;
    :cond_9
    invoke-virtual {v3}, Ljava/io/InputStreamReader;->close()V

    goto :goto_2

    .end local v0    # "br":Ljava/io/BufferedReader;
    .end local v4    # "line":Ljava/lang/String;
    :catch_7
    move-exception v9

    invoke-virtual {v7, v9}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_7

    :cond_a
    invoke-virtual {v3}, Ljava/io/InputStreamReader;->close()V
    :try_end_12
    .catch Ljava/lang/Throwable; {:try_start_12 .. :try_end_12} :catch_2
    .catchall {:try_start_12 .. :try_end_12} :catchall_4

    goto :goto_7

    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v4    # "line":Ljava/lang/String;
    :catch_8
    move-exception v7

    :try_start_13
    invoke-virtual {v6, v7}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_3

    :cond_b
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V

    goto :goto_3

    .end local v0    # "br":Ljava/io/BufferedReader;
    .end local v3    # "isr":Ljava/io/InputStreamReader;
    .end local v4    # "line":Ljava/lang/String;
    :catch_9
    move-exception v7

    invoke-virtual {v8, v7}, Ljava/lang/Throwable;->addSuppressed(Ljava/lang/Throwable;)V

    goto :goto_9

    :cond_c
    invoke-virtual {v2}, Ljava/io/InputStream;->close()V
    :try_end_13
    .catch Ljava/io/IOException; {:try_start_13 .. :try_end_13} :catch_3

    goto :goto_9

    .restart local v0    # "br":Ljava/io/BufferedReader;
    .restart local v3    # "isr":Ljava/io/InputStreamReader;
    :catchall_5
    move-exception v6

    move-object v7, v8

    goto :goto_4
.end method
