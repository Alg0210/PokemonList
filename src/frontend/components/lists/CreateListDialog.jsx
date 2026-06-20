import { useState } from 'react';
import { Plus, X } from 'lucide-react';

export default function CreateListDialog({ onClose, onSubmit }) {
  const [name, setName] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    const trimmedName = name.trim();

    if (!trimmedName) return;

    setIsSubmitting(true);
    await onSubmit(trimmedName);
    setIsSubmitting(false);
  }

  return (
    <div className="modal-backdrop" role="presentation">
      <div className="modal" role="dialog" aria-modal="true" aria-labelledby="create-list-title">
        <button className="icon-button modal-close" onClick={onClose} aria-label="Close dialog">
          <X size={18} aria-hidden="true" />
        </button>
        <h2 id="create-list-title">Create List</h2>
        <form onSubmit={handleSubmit}>
          <label>
            <span>List name</span>
            <input
              value={name}
              onChange={(event) => setName(event.target.value)}
              placeholder="Kanto starters"
              autoFocus
            />
          </label>
          <button className="primary-button modal-action" type="submit" disabled={!name.trim() || isSubmitting}>
            <Plus size={18} aria-hidden="true" />
            <span>{isSubmitting ? 'Creating...' : 'Create'}</span>
          </button>
        </form>
      </div>
    </div>
  );
}
