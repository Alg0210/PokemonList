import { apiRequest } from './client';

const CATALOG_BASE = '/api/pokemon-catalog';

export async function getGenerations() {
  const data = await apiRequest(CATALOG_BASE, '/generations');
  return Array.isArray(data) ? data : [];
}

export async function browsePokemonByGeneration(generationId) {
  const data = await apiRequest(CATALOG_BASE, `/generations/${generationId}/pokemon`);
  return Array.isArray(data) ? data : [];
}

export async function searchPokemon(query) {
  const params = `?query=${encodeURIComponent(query.trim())}`;
  const data = await apiRequest(CATALOG_BASE, `/pokemon/search${params}`);
  return Array.isArray(data) ? data : [];
}
