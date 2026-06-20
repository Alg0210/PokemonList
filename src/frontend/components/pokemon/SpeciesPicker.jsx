import { useEffect, useMemo, useState } from 'react';
import { Search } from 'lucide-react';
import { browsePokemonByGeneration, getGenerations, searchPokemon } from '../../api/catalog';
import { formatError } from '../../utils/formatError';
import { formatSpeciesName } from '../../utils/formatSpeciesName';

export default function SpeciesPicker({ value, onChange, existingSpecies = [] }) {
  const [generations, setGenerations] = useState([]);
  const [generationId, setGenerationId] = useState('');
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [isLoadingGenerations, setIsLoadingGenerations] = useState(true);
  const [isLoadingResults, setIsLoadingResults] = useState(false);
  const [error, setError] = useState('');
  const [catalogAvailable, setCatalogAvailable] = useState(true);

  const existingSet = useMemo(
    () => new Set(existingSpecies.map((name) => name.toLowerCase())),
    [existingSpecies]
  );

  const trimmedQuery = query.trim();
  const isSearchMode = trimmedQuery.length > 0;

  useEffect(() => {
    let cancelled = false;

    async function loadGenerations() {
      setIsLoadingGenerations(true);
      setError('');

      try {
        const data = await getGenerations();
        if (cancelled) return;

        setGenerations(data);
        setCatalogAvailable(true);
      } catch (err) {
        if (!cancelled) {
          setCatalogAvailable(false);
          setError(formatError(err));
        }
      } finally {
        if (!cancelled) {
          setIsLoadingGenerations(false);
        }
      }
    }

    loadGenerations();
    return () => {
      cancelled = true;
    };
  }, []);

  useEffect(() => {
    if (isLoadingGenerations || !catalogAvailable) return;

    if (isSearchMode) {
      let cancelled = false;
      const timer = setTimeout(async () => {
        setIsLoadingResults(true);
        setError('');

        try {
          const data = await searchPokemon(trimmedQuery);
          if (!cancelled) {
            setResults(data);
          }
        } catch (err) {
          if (!cancelled) {
            setError(formatError(err));
          }
        } finally {
          if (!cancelled) {
            setIsLoadingResults(false);
          }
        }
      }, 250);

      return () => {
        cancelled = true;
        clearTimeout(timer);
      };
    }

    if (!generationId) {
      setResults([]);
      setIsLoadingResults(false);
      return undefined;
    }

    let cancelled = false;

    async function loadGeneration() {
      setIsLoadingResults(true);
      setError('');

      try {
        const data = await browsePokemonByGeneration(generationId);
        if (!cancelled) {
          setResults(data);
        }
      } catch (err) {
        if (!cancelled) {
          setError(formatError(err));
        }
      } finally {
        if (!cancelled) {
          setIsLoadingResults(false);
        }
      }
    }

    loadGeneration();
    return () => {
      cancelled = true;
    };
  }, [generationId, trimmedQuery, isSearchMode, isLoadingGenerations, catalogAvailable]);

  function handleSelect(species) {
    if (existingSet.has(species.toLowerCase())) return;
    onChange(species);
  }

  function renderResults() {
    if (isLoadingResults) {
      return <div className="species-picker-status">Loading Pokemon...</div>;
    }

    if (isSearchMode && results.length === 0) {
      return <div className="species-picker-status">No Pokemon matched your search.</div>;
    }

    if (!isSearchMode && !generationId) {
      return (
        <div className="species-picker-status">
          Search for any Pokemon by name, or choose a generation to browse.
        </div>
      );
    }

    if (!isSearchMode && results.length === 0) {
      return <div className="species-picker-status">No Pokemon found in this generation.</div>;
    }

    if (isSearchMode) {
      return (
        <div className="species-result-grid">
          {results.map((item) => renderResultCard(item))}
        </div>
      );
    }

    return (
      <div className="species-result-grid">
        {results.map((item) => renderResultCard(item))}
      </div>
    );
  }

  function renderResultCard(item) {
    const isTaken = existingSet.has(item.species.toLowerCase());
    const isSelected = value === item.species;

    return (
      <button
        key={item.species}
        type="button"
        className={`species-result-card ${isSelected ? 'is-selected' : ''} ${isTaken ? 'is-disabled' : ''}`}
        onClick={() => handleSelect(item.species)}
        disabled={isTaken}
        title={isTaken ? 'Already in this list' : undefined}
      >
        <div className="species-result-sprite">
          {item.spriteUrl ? (
            <img src={item.spriteUrl} alt="" />
          ) : (
            <span>{item.species.slice(0, 1).toUpperCase()}</span>
          )}
        </div>
        <span>{formatSpeciesName(item.species)}</span>
      </button>
    );
  }

  if (!catalogAvailable) {
    return (
      <div className="species-picker">
        <label>
          <span>Species</span>
          <input
            value={value}
            onChange={(event) => onChange(event.target.value)}
            placeholder="pikachu"
            autoComplete="off"
          />
        </label>
        <p className="species-picker-fallback-note">
          Browse by generation is unavailable right now. Enter a species name manually, or restart the
          backend and refresh this page.
        </p>
        {error ? <div className="species-picker-error">{error}</div> : null}
      </div>
    );
  }

  return (
    <div className="species-picker">
      <div className="species-picker-controls">
        <label className="species-search-field">
          <span>Search species</span>
          <div className="species-search-input">
            <Search size={18} aria-hidden="true" />
            <input
              value={query}
              onChange={(event) => setQuery(event.target.value)}
              placeholder="Search all Pokemon..."
              autoComplete="off"
            />
          </div>
        </label>

        <label>
          <span>Browse by generation (optional)</span>
          <select
            value={generationId}
            onChange={(event) => setGenerationId(event.target.value)}
            disabled={isLoadingGenerations || isSearchMode}
          >
            <option value="">All generations</option>
            {generations.map((generation) => (
              <option key={generation.id} value={generation.id}>
                {generation.displayName || generation.name}
              </option>
            ))}
          </select>
        </label>
      </div>

      {value ? (
        <div className="species-selected">
          <span>Selected: {formatSpeciesName(value)}</span>
          <button type="button" className="text-button" onClick={() => onChange('')}>
            Clear
          </button>
        </div>
      ) : null}

      {error ? <div className="species-picker-error">{error}</div> : null}

      <div className="species-picker-results" aria-live="polite">
        {renderResults()}
      </div>
    </div>
  );
}
