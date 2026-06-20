import { Plus } from 'lucide-react';

export default function EmptyState({ onCreateList }) {
  return (
    <section className="empty-state">
      <h1>Start a Pokemon list</h1>
      <p>Create your first list, then add Pokemon!</p>
      <button className="primary-button" onClick={onCreateList}>
        <Plus size={18} aria-hidden="true" />
        <span>Create List</span>
      </button>
    </section>
  );
}
