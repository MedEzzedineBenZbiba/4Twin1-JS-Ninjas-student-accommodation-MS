import React, { useState } from 'react';
import BlocList from '../components/BlocList.jsx';
import BlocWeather from '../components/BlocWeather.jsx';

function BlocPage() {
    const [selectedBlocId, setSelectedBlocId] = useState(null);

    return (
        <div>
            <h1>Gestion des Blocs</h1>
            <BlocList onEdit={(bloc) => setSelectedBlocId(bloc.id)} />
            {selectedBlocId && <BlocWeather blocId={selectedBlocId} />}
        </div>
    );
}

export default BlocPage;