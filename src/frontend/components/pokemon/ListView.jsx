import { useState } from 'react';
import { Search } from 'lucide-react';
import PokemonCard from './PokemonCard';
import AddPokemonForm from './AddPokemonForm';
import EditPokemonDialog from './EditPokemonDialog';
import { formatSpeciesName } from '../../utils/formatSpeciesName';

export default function ListView({
  list,
  pokemon,
  isLoading,
  onAddPokemon,
  onUpdatePokemon,
  onDeletePokemon
}) {
  const [editingEntry, setEditingEntry] = useState(null);

  const existingSpecies = pokemon.map((entry) => entry.species);
  const editExistingSpecies = editingEntry
    ? pokemon.filter((entry) => entry.id !== editingEntry.id).map((entry) => entry.species)
    : existingSpecies;

  async function handleDelete(entry) {
    const label = formatSpeciesName(entry.nickname || entry.species);
    const confirmed = window.confirm(`Remove ${label} from this list?`);

    if (!confirmed) return;

    await onDeletePokemon(entry.id);
  }

  async function handleUpdate(values) {
    if (!editingEntry) return false;
    return onUpdatePokemon(editingEntry.id, values);
  }

  return (
    <section className="list-view">
      <div className="list-header">
        <div>
          <p className="eyebrow">Current list</p>
          <h1>{list.name}</h1>
        </div>
        <span className="count-pill">{pokemon.length} Pokemon</span>
      </div>

      <AddPokemonForm onAddPokemon={onAddPokemon} existingSpecies={existingSpecies} />

      {isLoading ? (
        <div className="status-panel">Loading Pokemon...</div>
      ) : pokemon.length === 0 ? (
        <div className="status-panel">
          <Search size={28} aria-hidden="true" />
          <span>No Pokemon in this list yet</span>
        </div>
      ) : (
        <div className="pokemon-grid">
          {pokemon.map((entry) => (
            <PokemonCard
              key={entry.id}
              entry={entry}
              onEdit={setEditingEntry}
              onDelete={handleDelete}
            />
          ))}
        </div>
      )}

      {editingEntry ? (
        <EditPokemonDialog
          entry={editingEntry}
          existingSpecies={editExistingSpecies}
          onClose={() => setEditingEntry(null)}
          onSubmit={handleUpdate}
        />
      ) : null}
    </section>
  );
}
