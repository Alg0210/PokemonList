import { useState } from 'react';
import { Save, X } from 'lucide-react';
import SpeciesPicker from './SpeciesPicker';

export default function EditPokemonDialog({ entry, existingSpecies = [], onClose, onSubmit }) {
  const [species, setSpecies] = useState(entry.species);
  const [nickname, setNickname] = useState(entry.nickname || '');
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    const trimmedSpecies = species.trim();
    const trimmedNickname = nickname.trim();

    if (!trimmedSpecies) return;

    setIsSubmitting(true);
    const saved = await onSubmit({
      species: trimmedSpecies,
      nickname: trimmedNickname
    });
    setIsSubmitting(false);

    if (saved) {
      onClose();
    }
  }

  return (
    <div className="modal-backdrop" role="presentation">
      <div className="modal modal-wide" role="dialog" aria-modal="true" aria-labelledby="edit-pokemon-title">
        <button className="icon-button modal-close" onClick={onClose} aria-label="Close dialog">
          <X size={18} aria-hidden="true" />
        </button>
        <h2 id="edit-pokemon-title">Edit Pokemon</h2>
        <form className="edit-pokemon-form" onSubmit={handleSubmit}>
          <SpeciesPicker
            value={species}
            onChange={setSpecies}
            existingSpecies={existingSpecies}
          />

          <label>
            <span>Nickname</span>
            <input
              value={nickname}
              onChange={(event) => setNickname(event.target.value)}
              placeholder="Sparky"
              autoComplete="off"
            />
          </label>

          <button
            className="primary-button modal-action"
            type="submit"
            disabled={!species.trim() || isSubmitting}
          >
            <Save size={18} aria-hidden="true" />
            <span>{isSubmitting ? 'Saving...' : 'Save changes'}</span>
          </button>
        </form>
      </div>
    </div>
  );
}
