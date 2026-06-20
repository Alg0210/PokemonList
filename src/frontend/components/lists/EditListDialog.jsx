import { useState } from 'react';
import { Save, X } from 'lucide-react';

export default function EditListDialog({ list, onClose, onSubmit }) {
  const [name, setName] = useState(list.name);
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    const trimmedName = name.trim();

    if (!trimmedName) return;

    setIsSubmitting(true);
    const saved = await onSubmit(trimmedName);
    setIsSubmitting(false);

    if (saved) {
      onClose();
    }
  }

  return (
    <div className="modal-backdrop" role="presentation">
      <div className="modal" role="dialog" aria-modal="true" aria-labelledby="edit-list-title">
        <button className="icon-button modal-close" onClick={onClose} aria-label="Close dialog">
          <X size={18} aria-hidden="true" />
        </button>
        <h2 id="edit-list-title">Rename List</h2>
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
            <Save size={18} aria-hidden="true" />
            <span>{isSubmitting ? 'Saving...' : 'Save changes'}</span>
          </button>
        </form>
      </div>
    </div>
  );
}
