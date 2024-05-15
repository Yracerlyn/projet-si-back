package com.projetsiback.projetsiback.service;

import com.projetsiback.projetsiback.models.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    public int generateSequence(String seqName) {
        // Vérifier si la séquence existe déjà
        Sequence sequence = mongoOperations.findById(seqName, Sequence.class);

        if (sequence == null) {
            // Si la séquence n'existe pas, créer une nouvelle séquence avec la valeur initiale
            sequence = new Sequence(seqName, 1); // ou une autre valeur initiale si nécessaire
            mongoOperations.save(sequence);
        }

        // Incrémenter la séquence et retourner la nouvelle valeur
        Sequence updatedSequence = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq", 1),
                Sequence.class);

        return updatedSequence.getSeq();
    }
}