package com.mercadolibre.bootcamp_demo_java_app.api.services.kvs;

import javax.annotation.PostConstruct;

import com.mercadolibre.json.exception.JsonException;
import com.mercadolibre.kvsclient.IContainerKvsClient;
import com.mercadolibre.kvsclient.exceptions.KvsException;
import com.mercadolibre.kvsclient.item.Item;

public abstract class KvsClientService<T> {
	private IContainerKvsClient kvs; 
	
	protected String containerName;
    
    public KvsClientService() {
    	super();         
    }
    
    @PostConstruct
    public void init() {
    	kvs = initKVS();
    }
    
    protected abstract IContainerKvsClient initKVS();
    
	protected abstract Class<T> getTypedClass();
    
	public T getValue(String key) throws KvsException, JsonException {
    	Item item = kvs.get(key);
    	return item != null ? item.getValueAsObjectType(getTypedClass()) : null;
    }
    
    public void saveValue(String key, T value, int ttl) throws KvsException {
    	Item item = new Item(); 
        item.setKey(key); 
        item.setValue(value); 
        item.setTtl(ttl);
        
        kvs.save(item);
    }
}
