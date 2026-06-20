import { Pencil, Trash2 } from 'lucide-react';

export default function ListNavItem({ list, isActive, onSelect, onEdit, onDelete }) {
  function handleDelete(event) {
    event.stopPropagation();
    onDelete(list);
  }

  function handleEdit(event) {
    event.stopPropagation();
    onEdit(list);
  }

  return (
    <div className={`list-nav-item hover-actions-host ${isActive ? 'is-active' : ''}`}>
      <button type="button" className="list-button" onClick={() => onSelect(list.id)}>
        <span className="list-button-label">{list.name}</span>
      </button>

      <div className="list-nav-actions hover-actions">
        <button
          type="button"
          className="icon-button card-action-button"
          onClick={handleEdit}
          aria-label={`Rename ${list.name}`}
        >
          <Pencil size={15} aria-hidden="true" />
        </button>
        <button
          type="button"
          className="icon-button card-action-button card-action-button-danger"
          onClick={handleDelete}
          aria-label={`Delete ${list.name}`}
        >
          <Trash2 size={15} aria-hidden="true" />
        </button>
      </div>
    </div>
  );
}
