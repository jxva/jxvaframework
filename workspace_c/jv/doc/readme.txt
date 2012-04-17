jv_pool    ->a memory pool

    jv_pool_t *jv_pool_create(size_t size);

    void *jv_pool_destroy(jv_pool_t *pool);

    void jv_pool_reset(jv_pool_t *pool);

    void *jv_pool_alloc(jv_pool_t *pool, size_t size);

    void *jv_pool_calloc(jv_pool_t *pool, size_t size);

    int jv_pool_free(jv_pool_t *pool, void *p);
    
--------------------------------------------------------------------------    
    
jv_db  -> a database connecting pool

    jv_db_t *jv_db_create();
    
    jv_connection_t *jv_db_connection(jv_db_t *db);
    
    void *jv_db_destroy(jv_db_t *db);
    
--------------------------------------------------------------------------
    
jv_thread    -> a multithreading pool

    jv_tpool_t *jv_thread_create(jv_pool_t *pool,uint_t min, uint_t max,uint_t linger, pthread_attr_t *attr);
    
    jv_int_t jv_thread_queue(jv_thread_t *thread,void *(*func)(void *), void *arg);
    
    void jv_thread_wait(jv_thread_t *thread);
    
    void jv_thread_destroy(jv_thread_t *thread);
    
    
    