import { Pencil, Trash2 } from 'lucide-react';
import { formatSpeciesName } from '../../utils/formatSpeciesName';

export default function PokemonCard({ entry, onEdit, onDelete }) {
  return (
    <article className="pokemon-card hover-actions-host">
      <div className="pokemon-card-actions hover-actions">
        <button
          type="button"
          className="icon-button card-action-button"
          onClick={() => onEdit(entry)}
          aria-label={`Edit ${formatSpeciesName(entry.species)}`}
        >
          <Pencil size={16} aria-hidden="true" />
        </button>
        <button
          type="button"
          className="icon-button card-action-button card-action-button-danger"
          onClick={() => onDelete(entry)}
          aria-label={`Delete ${formatSpeciesName(entry.species)}`}
        >
          <Trash2 size={16} aria-hidden="true" />
        </button>
      </div>

      <div className="sprite-frame">
        {entry.spriteUrl ? (
          <img src={entry.spriteUrl} alt={entry.species} />
        ) : (
          <span>{entry.species?.slice(0, 1).toUpperCase()}</span>
        )}
      </div>
      <div className="card-copy">
        <h2>{entry.nickname || entry.species}</h2>
        <p>{entry.species}</p>
      </div>
    </article>
  );
}
