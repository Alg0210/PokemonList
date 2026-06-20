import { useState } from 'react';
import { Plus } from 'lucide-react';
import SpeciesPicker from './SpeciesPicker';

export default function AddPokemonForm({ onAddPokemon, existingSpecies = [] }) {
  const [species, setSpecies] = useState('');
  const [nickname, setNickname] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  async function handleSubmit(event) {
    event.preventDefault();
    const trimmedSpecies = species.trim();
    const trimmedNickname = nickname.trim();

    if (!trimmedSpecies) return;

    setIsSubmitting(true);
    await onAddPokemon({
      species: trimmedSpecies,
      nickname: trimmedNickname
    });
    setSpecies('');
    setNickname('');
    setIsSubmitting(false);
  }

  return (
    <form className="add-pokemon-form" onSubmit={handleSubmit}>
      <div className="add-pokemon-picker">
        <SpeciesPicker
          value={species}
          onChange={setSpecies}
          existingSpecies={existingSpecies}
        />
      </div>

      <label>
        <span>Nickname</span>
        <input
          value={nickname}
          onChange={(event) => setNickname(event.target.value)}
          placeholder="Sparky"
          autoComplete="off"
        />
      </label>

      <button className="primary-button add-pokemon-submit" type="submit" disabled={isSubmitting || !species.trim()}>
        <Plus size={18} aria-hidden="true" />
        <span>{isSubmitting ? 'Adding...' : 'Add Pokemon'}</span>
      </button>
    </form>
  );
}
