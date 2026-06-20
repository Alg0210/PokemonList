import { Plus } from 'lucide-react';
import ListNavItem from './ListNavItem';

export default function Sidebar({
  lists,
  activeListId,
  isLoading,
  onSelectList,
  onCreateList,
  onEditList,
  onDeleteList
}) {
  return (
    <aside className="sidebar" aria-label="Pokemon lists">
      <button className="create-list-button" onClick={onCreateList}>
        <Plus size={18} aria-hidden="true" />
        <span>Create List</span>
      </button>

      <div className="list-nav">
        <div className="sidebar-label">Lists</div>
        {isLoading ? (
          <div className="muted">Loading lists...</div>
        ) : lists.length === 0 ? (
          <div className="muted">No lists yet</div>
        ) : (
          lists.map((list) => (
            <ListNavItem
              key={list.id}
              list={list}
              isActive={list.id === activeListId}
              onSelect={onSelectList}
              onEdit={onEditList}
              onDelete={onDeleteList}
            />
          ))
        )}
      </div>
    </aside>
  );
}
