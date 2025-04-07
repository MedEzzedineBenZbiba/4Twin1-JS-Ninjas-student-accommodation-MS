import React, { useState } from 'react';
import ChambreList from '../components/ChambreList';
import ChambreForm from '../components/ChambreForm';

function ChambrePage() {
    const [editingChambre, setEditingChambre] = useState(null);

    return (
        <div>
            <h1>Gestion des Chambres</h1>
            <ChambreForm chambreToEdit={editingChambre} />
            <ChambreList onEdit={setEditingChambre} />
        </div>
    );
}

export default ChambrePage;