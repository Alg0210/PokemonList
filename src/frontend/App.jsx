import { useState } from 'react';
import Topbar from './components/layout/Topbar';
import Sidebar from './components/layout/Sidebar';
import ListView from './components/pokemon/ListView';
import EmptyState from './components/lists/EmptyState';
import CreateListDialog from './components/lists/CreateListDialog';
import EditListDialog from './components/lists/EditListDialog';
import { usePokeLists } from './hooks/usePokeLists';

export default function App() {
  const {
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
  } = usePokeLists();

  const [editingList, setEditingList] = useState(null);

  async function handleDeleteList(list) {
    const confirmed = window.confirm(`Delete "${list.name}" and all Pokemon in it?`);

    if (!confirmed) return;

    await deleteList(list.id);
  }

  async function handleUpdateList(name) {
    if (!editingList) return false;
    return updateList(editingList.id, name);
  }

  return (
    <div className="app-shell">
      <Topbar />

      <div className="workspace">
        <Sidebar
          lists={lists}
          activeListId={activeListId}
          isLoading={isLoadingLists}
          onSelectList={setActiveListId}
          onCreateList={() => setIsCreateListOpen(true)}
          onEditList={setEditingList}
          onDeleteList={handleDeleteList}
        />

        <main className="content">
          {error ? <div className="error-banner">{error}</div> : null}

          {activeList ? (
            <ListView
              list={activeList}
              pokemon={pokemon}
              isLoading={isLoadingPokemon}
              onAddPokemon={addPokemon}
              onUpdatePokemon={updatePokemon}
              onDeletePokemon={deletePokemon}
            />
          ) : (
            <EmptyState onCreateList={() => setIsCreateListOpen(true)} />
          )}
        </main>
      </div>

      {isCreateListOpen ? (
        <CreateListDialog onClose={() => setIsCreateListOpen(false)} onSubmit={createList} />
      ) : null}

      {editingList ? (
        <EditListDialog
          list={editingList}
          onClose={() => setEditingList(null)}
          onSubmit={handleUpdateList}
        />
      ) : null}
    </div>
  );
}
