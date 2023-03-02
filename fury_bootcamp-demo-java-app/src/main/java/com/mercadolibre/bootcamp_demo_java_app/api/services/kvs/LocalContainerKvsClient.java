package com.mercadolibre.bootcamp_demo_java_app.api.services.kvs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.mercadolibre.common.async.Callback;
import com.mercadolibre.kvsclient.IContainerKvsClient;
import com.mercadolibre.kvsclient.exceptions.KvsException;
import com.mercadolibre.kvsclient.item.Item;
import com.mercadolibre.kvsclient.item.Items;

@Service
@Profile({"local","integration_test"})
public class LocalContainerKvsClient implements IContainerKvsClient {
	private Map<String,Item> map = new ConcurrentHashMap<>();

	@Override
	public Item get(String key) throws KvsException {
		return map.get(key);
	}

	@Override
	public void get(String key, Callback<Item> callback) {
		Item it = map.get(key);
		callback.success(it);
	}

	@Override
	public void save(Item item) throws KvsException {
		map.put(item.getKey(), item);
	}

	@Override
	public void save(Item item, Callback<Item> callback) {
		Item response = map.put(item.getKey(), item);
		callback.success(response);
	}

	@Override
	public void update(Item item) throws KvsException {
		map.put(item.getKey(), item);
	}

	@Override
	public void update(Item item, Callback<Item> callback) {
		Item response = map.put(item.getKey(), item);
		callback.success(response);
	}

	@Override
	public void delete(String key) throws KvsException {
		map.remove(key);
	}

	@Override
	public void delete(String key, Callback<Item> callback) {
		Item element = map.remove(key);
		callback.success(element);
	}

	@Override
	public Items batchGet(String[] keys) throws KvsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void batchGet(String[] keys, Callback<Items> callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void batchSave(Items items) throws KvsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void batchSave(Items items, Callback<Items> callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void batchUpdate(Items items) throws KvsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void batchUpdate(Items items, Callback<Items> callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void batchDelete(String[] keys) throws KvsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void batchDelete(String[] keys, Callback<Items> callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Items bulkGet(String[] keys) throws KvsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void bulkGet(String[] keys, Callback<Items> callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Items bulkSave(Items items) throws KvsException {
		throw new UnsupportedOperationException();

	}

	@Override
	public void bulkSave(Items items, Callback<Items> callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Items bulkUpdate(Items items) throws KvsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void bulkUpdate(Items items, Callback<Items> callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Items bulkDelete(String[] keys) throws KvsException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void bulkDelete(String[] keys, Callback<Items> callback) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shutdown() {
	}
}
