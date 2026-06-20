export async function apiRequest(basePath, path, options = {}) {
  const response = await fetch(`${basePath}${path}`, {
    headers: {
      'Content-Type': 'application/json',
      ...options.headers
    },
    ...options
  });

  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || `Request failed with ${response.status}`);
  }

  if (response.status === 204) {
    return null;
  }

  const text = await response.text();

  if (!text) {
    return null;
  }

  try {
    return JSON.parse(text);
  } catch {
    throw new Error('The backend returned a response the frontend could not read as JSON.');
  }
}

const POKE_LISTS_BASE = '/api/pokelists';

export async function request(path, options = {}) {
  return apiRequest(POKE_LISTS_BASE, path, options);
}
