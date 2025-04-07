import React, { useEffect, useState } from 'react';
import '../api/blocApi';
import { Button } from '@mui/material';

function BlocList({ onSelectBloc }) {
    const [blocs, setBlocs] = useState([]);

    useEffect(() => {
        fetchBlocs();
    }, []);

    const fetchBlocs = async () => {
        const data = await getAll();
        setBlocs(data);
    };

    const handleDelete = async (id) => {
        await remove(id);
        fetchBlocs();
    };

    return (
        <div>
            <h2>Liste des Blocs</h2>
            <ul>
                {blocs.map(bloc => (
                    <li key={bloc.id}>
                        {bloc.nomBloc} (Capacité: {bloc.capaciteBloc})
                        <Button onClick={() => onSelectBloc(bloc.id)} sx={{ ml: 2 }}>Météo</Button>
                        <Button onClick={() => handleDelete(bloc.id)} sx={{ ml: 1 }} color="error">Supprimer</Button>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default BlocList;
