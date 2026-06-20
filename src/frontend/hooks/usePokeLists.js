import { useEffect, useMemo, useState } from 'react';
import * as pokelistsApi from '../api/pokelists';
import { formatError } from '../utils/formatError';

export function usePokeLists() {
  const [lists, setLists] = useState([]);
  const [activeListId, setActiveListId] = useState(null);
  const [pokemon, setPokemon] = useState([]);
  const [isCreateListOpen, setIsCreateListOpen] = useState(false);
  const [isLoadingLists, setIsLoadingLists] = useState(true);
  const [isLoadingPokemon, setIsLoadingPokemon] = useState(false);
  const [error, setError] = useState('');

  const activeList = useMemo(
    () => lists.find((list) => list.id === activeListId) ?? null,
    [lists, activeListId]
  );

  useEffect(() => {
    loadLists();
  }, []);

  useEffect(() => {
    if (activeListId) {
      loadPokemon(activeListId);
    } else {
      setPokemon([]);
    }
  }, [activeListId]);

  async function loadLists() {
    setIsLoadingLists(true);
    setError('');

    try {
      const nextLists = await pokelistsApi.getLists();
      setLists(nextLists);

      setActiveListId((current) => {
        if (!current && nextLists.length > 0) {
          return nextLists[0].id;
        }
        return current;
      });
    } catch (err) {
      setError(formatError(err));
    } finally {
      setIsLoadingLists(false);
    }
  }

  async function loadPokemon(listId) {
    setIsLoadingPokemon(true);
    setError('');

    try {
      const data = await pokelistsApi.getPokemonInList(listId);
      setPokemon(data);
    } catch (err) {
      setError(formatError(err));
    } finally {
      setIsLoadingPokemon(false);
    }
  }

  async function createList(name) {
    setError('');

    try {
      const created = await pokelistsApi.createList(name);

      setLists((current) => [...current, created]);
      setActiveListId(created.id);
      setIsCreateListOpen(false);
    } catch (err) {
      setError(formatError(err));
    }
  }

  async function updateList(listId, name) {
    setError('');

    try {
      const updated = await pokelistsApi.updateList(listId, name);
      setLists((current) => current.map((list) => (list.id === listId ? updated : list)));
      return true;
    } catch (err) {
      setError(formatError(err));
      return false;
    }
  }

  async function deleteList(listId) {
    setError('');

    try {
      await pokelistsApi.deleteList(listId);

      setLists((current) => {
        const nextLists = current.filter((list) => list.id !== listId);

        setActiveListId((currentActiveId) => {
          if (currentActiveId !== listId) {
            return currentActiveId;
          }
          return nextLists[0]?.id ?? null;
        });

        return nextLists;
      });

      return true;
    } catch (err) {
      setError(formatError(err));
      return false;
    }
  }

  async function addPokemon(values) {
    if (!activeListId) return;

    setError('');

    try {
      const created = await pokelistsApi.addPokemon(activeListId, values);
      setPokemon((current) => [...current, created]);
    } catch (err) {
      setError(formatError(err));
    }
  }

  async function updatePokemon(entryId, values) {
    if (!activeListId) return false;

    setError('');

    try {
      const updated = await pokelistsApi.updatePokemon(activeListId, entryId, values);
      setPokemon((current) => current.map((entry) => (entry.id === entryId ? updated : entry)));
      return true;
    } catch (err) {
      setError(formatError(err));
      return false;
    }
  }

  async function deletePokemon(entryId) {
    if (!activeListId) return false;

    setError('');

    try {
      await pokelistsApi.deletePokemon(activeListId, entryId);
      setPokemon((current) => current.filter((entry) => entry.id !== entryId));
      return true;
    } catch (err) {
      setError(formatError(err));
      return false;
    }
  }

  return {
    lists,
    activeList,
    activeListId,
    setActiveListId,
    pokemon,
    error,
    isLoadingLists,
    isLoadingPokemon,
    isCreateListOpen,
    setIsCreateListOpen,
    createList,
    updateList,
    deleteList,
    addPokemon,
    updatePokemon,
    deletePokemon
  };
}
