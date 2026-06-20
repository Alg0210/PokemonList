import { request } from './client';

export async function getLists() {
  const data = await request('');
  return Array.isArray(data) ? data : [];
}

export async function createList(name) {
  return request('/new-list', {
    method: 'POST',
    body: JSON.stringify({ name })
  });
}

export async function updateList(listId, name) {
  return request(`/${listId}`, {
    method: 'PUT',
    body: JSON.stringify({ name })
  });
}

export async function deleteList(listId) {
  return request(`/${listId}`, {
    method: 'DELETE'
  });
}

export async function getPokemonInList(listId) {
  const data = await request(`/${listId}/pokemon/all-pokemon`);
  return Array.isArray(data) ? data : [];
}

export async function addPokemon(listId, values) {
  return request(`/${listId}/pokemon/add-pokemon`, {
    method: 'POST',
    body: JSON.stringify(values)
  });
}

export async function updatePokemon(listId, entryId, values) {
  return request(`/${listId}/pokemon/${entryId}`, {
    method: 'PUT',
    body: JSON.stringify(values)
  });
}

export async function deletePokemon(listId, entryId) {
  return request(`/${listId}/pokemon/${entryId}`, {
    method: 'DELETE'
  });
}
